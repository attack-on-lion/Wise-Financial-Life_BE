package com.wisespendinglife.wise_spending_life.domain.solution.service;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentService;
import com.wisespendinglife.wise_spending_life.domain.solution.converter.SolutionConverter;
import com.wisespendinglife.wise_spending_life.domain.solution.dto.SimpleSolutionResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.solution.repository.SolutionRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserReadService;
import com.wisespendinglife.wise_spending_life.global.ai.AiChatGateway;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SolutionServiceImpl implements SolutionService{

    private final PaymentService paymentService;
    private final UserReadService userReadService;
    private final SolutionRepository solutionRepository;
    private final SolutionConverter solutionConverter;
    private final AiChatGateway aiChatGateway;

    @Override
    public SimpleSolutionResponseDTO getMonthlyComparisonSolution(Long userId) {

        User user = userReadService.getEntity(userId);

        LocalDate today = LocalDate.now();
        LocalDate thisMonthStart = today.withDayOfMonth(1);
        LocalDate thisMonthEnd = today;
        LocalDate prevMonthStart = thisMonthStart.minusMonths(1);
        LocalDate prevMonthEnd = thisMonthStart.minusDays(1);

        log.info(">>> [SERVICE] 유저 심플 AI 솔루션(전월&현월) - User: {}, prevMonth: {}, thisMonth: {}", user.getId(), prevMonthStart, thisMonthEnd);

        PaymentResponseDto.Payments prevPayments = paymentService.getMonthly(
                prevMonthStart, prevMonthEnd, 0, 10_000, userId, Optional.empty()
        );
        PaymentResponseDto.Payments thisPayments = paymentService.getMonthly(
                thisMonthStart, thisMonthEnd, 0, 10_000, userId, Optional.empty()
        );

        Map<String, long[]> prevCatAgg = getByCategoryAvg(prevPayments);
        Map<String, long[]> thisCatAgg = getByCategoryAvg(thisPayments);

        // 증가한 카테고리 TOP 4 (전월 대비 현월)
        var topIncreased = topIncreasedCategories(prevCatAgg, thisCatAgg, 4);

        // 프롬프트 생성 (전월/현월 비교 상세)
        String userPrompt = buildMonthlyDiffPrompt(
                prevPayments, thisPayments,
                prevCatAgg, thisCatAgg,
                prevMonthStart, prevMonthEnd,
                thisMonthStart, thisMonthEnd,
                topIncreased
        );

        String systemPrompt = """
        너는 금융상담사야. 사용자의 전월과 현월 소비를 비교해 한 줄 메시지와 두 개의 절약 솔루션을 한국어(존댓말)로 제안한다.
        분석은 숫자/금액을 나열하지 말고 변화 포인트 중심으로, 행동 가능한 팁 2가지를 제안해줘.
        출력은 반드시 다음 JSON 형식으로만 응답해.
        {
          "message": "한 줄 요약 메시지",
          "solution": ["솔루션 1", "솔루션 2"]
        }
        """;

        String aiSolution = aiChatGateway.ai(systemPrompt, userPrompt);

        // 파싱 → DTO
        SimpleSolutionResponseDTO dto = solutionConverter.toResponseDto(aiSolution);

        log.info(">>> [GPT] 유저 심플 AI 솔루션(전월&현월) - result: {}", dto);

        // 저장
        solutionRepository.save(solutionConverter.toEntity(user, dto));

        return dto;

    }


    /** 전월 대비 현월 증가 카테고리 TOP K 계산 */
    private static java.util.List<CategoryDiff> topIncreasedCategories(Map<String, long[]> prevAgg,
                                                                       Map<String, long[]> currAgg,
                                                                       int k) {
        java.util.Set<String> keys = new java.util.HashSet<>();
        keys.addAll(prevAgg.keySet());
        keys.addAll(currAgg.keySet());

        java.util.List<CategoryDiff> diffs = keys.stream()
                .map(cat -> {
                    long prevSum = prevAgg.getOrDefault(cat, new long[]{0L, 0L})[0];
                    long prevCnt = prevAgg.getOrDefault(cat, new long[]{0L, 0L})[1];
                    long currSum = currAgg.getOrDefault(cat, new long[]{0L, 0L})[0];
                    long currCnt = currAgg.getOrDefault(cat, new long[]{0L, 0L})[1];
                    long delta = currSum - prevSum;
                    double pct = (prevSum == 0) ? (currSum > 0 ? 100.0 : 0.0) : (delta * 100.0 / prevSum);
                    return new CategoryDiff(cat, prevSum, prevCnt, currSum, currCnt, delta, pct);
                })
                .filter(d -> d.delta() > 0)
                .sorted(java.util.Comparator.comparingLong(CategoryDiff::delta).reversed())
                .limit(k)
                .toList();

        return diffs;
    }

    /** 전월/현월 비교 프롬프트 생성 (증가 카테고리 중심) */
    private String buildMonthlyDiffPrompt(PaymentResponseDto.Payments prevPayments,
                                          PaymentResponseDto.Payments currPayments,
                                          Map<String, long[]> prevAgg,
                                          Map<String, long[]> currAgg,
                                          LocalDate prevStart,
                                          LocalDate prevEnd,
                                          LocalDate currStart,
                                          LocalDate currEnd,
                                          java.util.List<CategoryDiff> topIncreased) {

        long prevTotal = prevPayments.getSummary().getTotalExpense();
        long currTotal = currPayments.getSummary().getTotalExpense();
        int prevCount = prevPayments.getSummary().getCount();
        int currCount = currPayments.getSummary().getCount();

        StringBuilder sb = new StringBuilder();
        sb.append("입력 요약:\n");
        sb.append("- 전월 기간: ").append(prevStart).append(" ~ ").append(prevEnd).append("\n");
        sb.append("- 현월 기간: ").append(currStart).append(" ~ ").append(currEnd).append("\n");
        sb.append("- 전월 지출합: ").append(prevTotal).append("원, 거래수: ").append(prevCount).append("건\n");
        sb.append("- 현월 지출합: ").append(currTotal).append("원, 거래수: ").append(currCount).append("건\n");

        sb.append("\n증가 카테고리(최대 4개):\n");
        for (CategoryDiff d : topIncreased) {
            sb.append("- 카테고리: ").append(d.category()).append("\n")
                    .append("  · 전월: ").append(d.prevSum()).append("원(").append(d.prevCnt()).append("건)\n")
                    .append("  · 현월: ").append(d.currSum()).append("원(").append(d.currCnt()).append("건)\n")
                    .append("  · 증감: +").append(d.delta()).append("원")
                    .append(d.prevSum() == 0 ? " (신규)" : " (+" + Math.round(d.pct()) + "%)").append("\n");
        }

        sb.append("\n지시사항:\n")
                .append("- 위 증가 카테고리를 중심으로 변화 원인을 추정하고, 각 카테고리에 대해 불필요 지출을 줄일 수 있는 간단한 행동 팁을 제안하세요.\n")
                .append("- 숫자 나열은 최소화하고 자연스러운 문장으로 설명하세요.\n");

        return sb.toString();
    }

    /** 전월/현월 카테고리 비교 결과 */
    private record CategoryDiff(String category,
                                long prevSum, long prevCnt,
                                long currSum, long currCnt,
                                long delta, double pct) {}

    @Override
    public SimpleSolutionResponseDTO getSimpleSolutionMonthly(Long userId) {
        User user = userReadService.getEntity(userId);

        // 이번 달 기간
        LocalDate today = LocalDate.now();
        LocalDate from = today.withDayOfMonth(1);
        LocalDate to = today;

        // 결제 내역 (이번 달 전부: 페이지 크게)
        PaymentResponseDto.Payments payments = paymentService.getMonthly(
                from, to, 0, 10_000, userId, Optional.empty()
        );

        // 카테고리별 합계/횟수 집계
        Map<String, long[]> catAgg = getByCategoryAvg(payments);

        String catAggLog = catLogString(catAgg);

        log.info(">>> [GPT] 유저 심플 AI 솔루션 - User: {}, catAgg: {}", user.getId(), catAggLog);

        // 프롬프트 생성
        String userPrompt = buildPrompt(payments, catAgg);

        String systemPrompt = """
        너는 금융상담사야. 사용자의 소비를 간단히 분석해 한 줄 메시지와 두 개의 절약 솔루션을 밝고 명쾌하게 한국어(존댓말)로 제안한다.
        분석은 숫자/금액을 나열하지 말고, 자연스러운 한국어 문장으로 설명해줘.
        그리고 말 끝 마다 ! 를 붙여야해. 그리고 말투는 친근하고 걱정스러운 말투로 해줘.
        출력은 반드시 다음 JSON 형식으로만 응답해.
        {
          "message": "한 줄 요약 메시지",
          "solution": ["솔루션 1", "솔루션 2"]
        }
        """;


        // Ai 호출
        String aiJson = aiChatGateway.ai(systemPrompt, userPrompt);

        // 파싱 → DTO
        SimpleSolutionResponseDTO dto = solutionConverter.toResponseDto(aiJson);
        log.info(">>> [GPT] 유저 심플 AI 솔루션 - result: {}", dto);

        // 데이터 저장
        solutionRepository.save(solutionConverter.toEntity(user, dto));

        // 그대로 반환
        return dto;
    }


    // 카테고리별 합계/횟수 집계
    private static Map<String, long[]> getByCategoryAvg(PaymentResponseDto.Payments payments) {
        Map<String, long[]> catAgg = payments.getItems().stream()
                .filter(i -> i.getCategory() != null)
                .collect(Collectors.groupingBy(
                        i -> i.getCategory().getName(),
                        Collectors.reducing(new long[]{0L, 0L},
                                i -> new long[]{i.getAmount(), 1L},
                                (a, b) -> new long[]{a[0] + b[0], a[1] + b[1]}
                        )
                ));
        return catAgg;
    }


    /**
     * 프롬프트 구성
     * @param payments - 결제 내역
     * @param catAgg - 카테고리별 합계/횟수 집계
     * @return
     */
    private String buildPrompt(PaymentResponseDto.Payments payments, Map<String, long[]> catAgg) {
        // 최소 컨텍스트: 총지출/수입, 상위 카테고리 1~2개, 이번달이라는 맥락
        var summary = payments.getSummary();
        String topCats = catAgg.entrySet().stream()
                .sorted((a,b) -> Long.compare(b.getValue()[0], a.getValue()[0]))
                .limit(2)
                .map(e -> e.getKey() + ":" + e.getValue()[0] + "원(" + e.getValue()[1] + "건)")
                .collect(Collectors.joining(", "));

        // 반드시 아래 JSON 스키마로만 답변하도록 강제
        return """
        규칙:
        - 솔루션은 행동 팁 또는 일반화 가능한 가이드 위주(2개).

        입력 요약:
        - 기간: %s ~ %s
        - 총지출: %d원, 총수입: %d원, 거래수: %d건
        - 상위 카테고리: %s
        """.formatted(
                payments.getSummary().getPeriod().getFrom(),
                payments.getSummary().getPeriod().getTo(),
                payments.getSummary().getTotalExpense(),
                payments.getSummary().getTotalIncome(),
                payments.getSummary().getCount(),
                topCats.isEmpty() ? "없음" : topCats
        );
    }

    private String catLogString(Map<String, long[]> catAgg) {
        return catAgg.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue()[0] + "원/" + e.getValue()[1] + "건")
                .collect(Collectors.joining(", ", "{", "}"));
    }
}
