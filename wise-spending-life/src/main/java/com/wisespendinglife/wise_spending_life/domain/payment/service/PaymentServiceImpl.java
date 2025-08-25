package com.wisespendinglife.wise_spending_life.domain.payment.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.payment.assembler.PaymentResponseAssembler;
import com.wisespendinglife.wise_spending_life.domain.payment.converter.PaymentConverter;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentDirection;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentMiniDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import com.wisespendinglife.wise_spending_life.domain.payment.repository.CategoryRiseRow;
import com.wisespendinglife.wise_spending_life.domain.payment.repository.PaymentRepository;
import com.wisespendinglife.wise_spending_life.domain.score.dto.CategoryState;
import com.wisespendinglife.wise_spending_life.domain.score.dto.MonthlyState;
import com.wisespendinglife.wise_spending_life.domain.score.dto.ScoreResponseDto;
import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import com.wisespendinglife.wise_spending_life.domain.score.repository.ScoreRepository;
import com.wisespendinglife.wise_spending_life.domain.score.service.ChatGptScoringClient;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Optional;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentConverter paymentConverter;
    private final CategoryRepository categoryRepository;
    private final PaymentConverter converter;
    private final PaymentResponseAssembler responseAssembler;
    private final ChatGptScoringClient scoringClient;
    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;


    public interface DailyAggregate {
        LocalDate getDate();
        Long getTotalExpense();
        Integer getTransactionCount();
    }

    public interface CategoryAggregate {
        Long getCategoryId();  // alias: categoryId
        String getCategoryName();  // alias: categoryName
        Long getAmount();  // alias: amount  (원)
        Integer getTransactionCount();  // alias: transactionCount
    }

    public PaymentResponseDto.Payments getMonthly(LocalDate from,
                                                  LocalDate to,
                                                  int currentPage,
                                                  int size,
                                                  Long userId,
                                                  Optional<String> categoryOpt
                                                  ) {

        if(from.isAfter(to)) throw new BusinessException(ErrorCode.INVALID_DATE_REQUEST);

        // 유저 확인
        userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        LocalDateTime start = from.atStartOfDay();
        LocalDateTime end   = to.plusDays(1).atStartOfDay();

        // Pageable 설정
        Pageable pageable = PageRequest.of(currentPage, size, Sort.by(Sort.Order.desc("transactionAt"),
                Sort.Order.desc("id")).descending());

        // Page 조회
        Page<Payment> page = categoryOpt
                .map(cat -> {
                    // ❶ 존재 여부 검증
                    if (!categoryRepository.existsByNameIgnoreCase(cat))
                        throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);

                    // ❷ 정상이라면 조회
                    return paymentRepository
                            .findByUser_IdAndCategory_NameIgnoreCaseAndTransactionAtBetween(
                                    userId, cat, start, end, pageable);
                })
                .orElseGet(() -> paymentRepository
                        .findByUserIdAndTransactionAtBetween(userId, start, end, pageable));

        // Response 생성
        return responseAssembler.assemble(from, to, page);
    }

    /**
     * Payment 생성
     *
     * @param dto - 생성 요청 DTO
     * @param userId - 사용자 ID
     * @return - 생성 결과 DTO
     */
    public PaymentResponseDto.PaymentCreateResponseDto create(PaymentRequestDto.CreateDto dto, Long userId) {

        // 1) 카테고리 찾기 (대소문자 무시)
        Category category = categoryRepository.findByNameIgnoreCase(dto.getCategory())
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 2) 엔티티 변환 & 저장
        Payment payment = converter.toEntity(dto, category, user);
        Payment saved = paymentRepository.save(payment);

        // 3) 응답 DTO 변환
        return converter.toCreateResponseDto(saved.getId());
    }

    /**
     * 사용자의 월별 점수 계산
     *
     * @param userId 사용자 ID
     * @return
     */
    public ScoreResponseDto calculateMonthlyScore(Long userId) {

        // 1) 전월 구간(KST)
        YearMonth last = YearMonth.now(ZoneId.of("Asia/Seoul")).minusMonths(1);
        LocalDateTime start = last.atDay(1).atStartOfDay();
        LocalDateTime end = last.atEndOfMonth().atTime(LocalTime.MAX);

        // 2) 통계 조회
        MonthlyState base =
                paymentRepository.findIncomeAndOutflowByUserId(start, end, userId);
        List<CategoryState> detail =
                paymentRepository.findCategoryStatsByUserId(start, end, userId);

        // 3) DTO 완성
        MonthlyState stats = MonthlyState.builder()
                .totalIncome(base.getTotalIncome())
                .totalOutflow(base.getTotalOutflow())
                .categoryStates(detail)
                .build();

        // 4) 점수 요청
        int score = scoringClient.askScore(stats);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // 5) 결과 저장 (선택)
        scoreRepository.save(
                Score.builder()
                        .score(score)
                        .user(user)
                        .build()
        );

        ScoreResponseDto response = ScoreResponseDto.builder()
                .score(score)
                .build();

        return response;
    }


    /**
     * 이번 주 월요일(00:00, Asia/Seoul)부터 지금까지의 "일자별 총 지출"을 월~오늘까지 반환.
     * 지출이 없는 날은 0으로 채워서 모두 포함.
     */
    public PaymentResponseDto.WeeklyDailyTotals getWeeklyDailyTotals(Long userId) {
        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);
        LocalDate monday = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        List<DailyAggregate> aggregates = paymentRepository.sumDailyExpenseByDate(
                userId, monday.atStartOfDay(), LocalDateTime.now(zone)
        );

        return paymentConverter.toWeeklyDailyTotals(userId, monday, today, aggregates);
    }

    // ----- 헬퍼들 -----

    private static Stream<LocalDate> datesBetweenInclusive(LocalDate start, LocalDate end) {
        long days = Duration.between(start.atStartOfDay(), end.plusDays(1).atStartOfDay()).toDays();
        return Stream.iterate(start, d -> d.plusDays(1)).limit(days);
    }

    private static String toKo(DayOfWeek dow) {
        // 1=MON … 7=SUN
        switch (dow) {
            case MONDAY: return "월";
            case TUESDAY: return "화";
            case WEDNESDAY: return "수";
            case THURSDAY: return "목";
            case FRIDAY: return "금";
            case SATURDAY: return "토";
            case SUNDAY: return "일";
            default: return "";
        }
    }

    public PaymentResponseDto.MonthlyTopCategories getMonthlyTop3Categories(Long userId) {
        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDate today = LocalDate.now(zone);
        LocalDate firstDay = today.withDayOfMonth(1);

        List<CategoryAggregate> aggs = paymentRepository.sumMonthlyExpenseByCategory(
                userId, firstDay.atStartOfDay(), LocalDateTime.now(zone)
        );

        return paymentConverter.toMonthlyTop3Categories(userId, firstDay, today, aggs);
    }

    @Override
    public List<PaymentMiniDto> getPaymentMiniList(Long userId, LocalDateTime from, LocalDateTime to) {
        if (userId == null){
            throw new BusinessException(ErrorCode.INVALID_USER_ID);
        }
        if (from == null | to == null){
            throw new BusinessException(ErrorCode.INVALID_DATE_REQUEST);
        }

        LocalDateTime start = from;
        LocalDateTime end = to;

        Page<Payment> page = paymentRepository.findByUserIdAndTransactionAtBetween(
                userId, start, end, Pageable.unpaged()
        );

        List<PaymentMiniDto> minis = page.getContent().stream()
                .filter(p -> p.getDirection() == PaymentDirection.OUTFLOW)
                .map(this::toMini)
                .toList();

        if (minis.isEmpty()){
            throw new BusinessException(ErrorCode.PAYMENT_NOT_FOUND);
        }
        return minis;
    }

    private PaymentMiniDto toMini(Payment payment) {
        return PaymentMiniDto.builder()
                .transactionAt(payment.getTransactionAt())
                .category(payment.getCategory().getName())
                .build();
    }

    @Override
    public PaymentResponseDto.CategoryRiseItemListDto getCategoryRiseItemList(Long userId) {

        ZoneId zone = ZoneId.of("Asia/Seoul");

        // 현월
        LocalDate today = LocalDate.now(zone);
        LocalDate firstDay = today.withDayOfMonth(1);
        LocalDateTime currStart = firstDay.atStartOfDay();
        LocalDateTime currEnd = today.atTime(LocalTime.MAX);

        // 전월
        LocalDate prevMonthFirst = firstDay.minusMonths(1);
        LocalDateTime prevStart = prevMonthFirst.atStartOfDay();
        LocalDateTime prevEnd = firstDay.atStartOfDay();


        List<CategoryRiseRow> repoResult = paymentRepository.findTopCategoryRises(
                userId, PaymentDirection.OUTFLOW,
                prevStart, prevEnd, currStart, currEnd,
                PageRequest.of(0, 4) // 상위 4개
        );

        AtomicInteger ranker = new AtomicInteger(1);
        List<PaymentResponseDto.CategoryRiseItem> list = repoResult.stream()
                .map(r -> paymentConverter.toCategoryRiseItem(r, ranker.getAndIncrement()))
                .toList();

        return paymentConverter.toCategoryRiseItemListDto(list);
    }
}
