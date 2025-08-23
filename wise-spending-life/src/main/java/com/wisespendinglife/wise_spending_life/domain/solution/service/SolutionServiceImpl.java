package com.wisespendinglife.wise_spending_life.domain.solution.service;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentService;
import com.wisespendinglife.wise_spending_life.domain.solution.converter.SolutionConverter;
import com.wisespendinglife.wise_spending_life.domain.solution.dto.SimpleSolutionResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.solution.repository.SolutionRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.ai.AiChatGateway;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SolutionServiceImpl implements SolutionService{

    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final SolutionRepository solutionRepository;
    private final SolutionConverter solutionConverter;
    private final AiChatGateway aiChatGateway;

    @Override
    @Transactional
    public SimpleSolutionResponseDTO getSimpleSolution(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND)); // 네 에러 체계에 맞춰 변경

        // 이번 달 기간
        LocalDate today = LocalDate.now();
        LocalDate from = today.withDayOfMonth(1);
        LocalDate to = today;

        // 결제 내역 (이번 달 전부: 페이지 크게)
        PaymentResponseDto.Payments payments = paymentService.getMonthly(
                from, to, 0, 10_000, userId, Optional.empty()
        );

        // 카테고리별 합계/횟수 집계
        Map<String, long[]> catAgg = payments.getItems().stream()
                .filter(i -> i.getCategory() != null)
                .collect(Collectors.groupingBy(
                        i -> i.getCategory().getName(),
                        Collectors.reducing(new long[]{0L, 0L},
                                i -> new long[]{i.getAmount(), 1L},
                                (a, b) -> new long[]{a[0] + b[0], a[1] + b[1]}
                        )
                ));

        String catAggLog = catLogString(catAgg);

        log.info(">>> [GPT] 유저 심플 AI 솔루션 - User: {}, catAgg: {}", user.getId(), catAggLog);

        // 프롬프트 생성
        String userPrompt = buildPrompt(payments, catAgg);

        String systemPrompt = """
        너는 사용자의 소비를 간단히 분석해 한 줄 메시지와 두 개의 절약 솔루션을 밝고 명쾌하게 한국어(존댓말)로 제안한다.
        말 끝 마다 ! 를 붙여야해.
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
        - 금액은 원 단위로, 과한 확정 표현 금지(예: "~ 가능" 정도).
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
