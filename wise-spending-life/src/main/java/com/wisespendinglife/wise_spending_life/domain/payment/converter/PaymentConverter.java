package com.wisespendinglife.wise_spending_life.domain.payment.converter;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import com.wisespendinglife.wise_spending_life.domain.payment.repository.CategoryRiseRow;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentServiceImpl;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Payment Converter
 */

@Component
@RequiredArgsConstructor
public class PaymentConverter {

    public PaymentResponseDto.CategoryRiseItem toCategoryRiseItem(CategoryRiseRow row, Integer rank) {
        return PaymentResponseDto.CategoryRiseItem.builder()
                .categoryId(row.getCategoryId())
                .categoryName(row.getCategoryName())
                .totalCurrent(row.getCurrTotal())
                .totalPrevious(row.getPrevTotal())
                .rank(rank)
                .build();
    }

    public PaymentResponseDto.CategoryRiseItemListDto toCategoryRiseItemListDto(List<PaymentResponseDto.CategoryRiseItem> items) {
        return PaymentResponseDto.CategoryRiseItemListDto
                .builder()
                .items(items)
                .build();
    }

    // Entity -> DTO(Item)
    public PaymentResponseDto.Item toItemDto(Payment e) {
        return PaymentResponseDto.Item.builder()
                .id(e.getId())
                .transactionAt(e.getTransactionAt())
                .storeName(e.getStoreName())
                .amount(e.getAmount())
                .direction(e.getDirection())
                .method(e.getMethod())
                .category(
                        PaymentResponseDto.Category.builder()
                                .id(e.getCategory().getId())
                                .name(e.getCategory().getName())
                                .build())
                .build();
    }

    /**
     * Payment Entity List -> DTO List(Item)
     * @param list
     * @return
     */
    public List<PaymentResponseDto.Item> toItemDtos(List<Payment> list) {
        return list.stream()
                .map(this::toItemDto)
                .toList();
    }

    public Payment toEntity(PaymentRequestDto.CreateDto dto, Category category, User user) {
        return Payment.builder()
                .transactionAt(dto.getTransactionAt())
                .storeName(dto.getStoreName())
                .amount(dto.getAmount())
                .direction(dto.getDirection())
                .method(dto.getMethod())
                .user(user)
                .category(category)
                .build();
    }

    public PaymentResponseDto.PaymentCreateResponseDto toCreateResponseDto(Long id) {
        return PaymentResponseDto.PaymentCreateResponseDto.builder()
                .id(id)
                .build();
    }

    /**
     * Projection -> DailyTotal DTO 변환
     */
    public PaymentResponseDto.DailyTotal toDailyTotal(PaymentServiceImpl.DailyAggregate agg) {
        return PaymentResponseDto.DailyTotal.builder()
                .date(agg.getDate())
                .dayOfWeekKo(toKo(agg.getDate().getDayOfWeek()))
                .totalExpense(agg.getTotalExpense() == null ? 0L : agg.getTotalExpense())
                .transactionCount(agg.getTransactionCount() == null ? 0 : agg.getTransactionCount())
                .build();
    }

    /**
     * Projection List + 기간정보 -> WeeklyDailyTotals DTO 변환
     * 누락 날짜(지출 0원)도 채워넣음
     */
    public PaymentResponseDto.WeeklyDailyTotals toWeeklyDailyTotals(
            Long userId, LocalDate monday, LocalDate today, List<PaymentServiceImpl.DailyAggregate> aggregates) {

        // Map<날짜, Agg>로 변환
        Map<LocalDate, PaymentServiceImpl.DailyAggregate> byDate = aggregates.stream()
                .collect(Collectors.toMap(PaymentServiceImpl.DailyAggregate::getDate, a -> a));

        List<PaymentResponseDto.DailyTotal> days = datesBetweenInclusive(monday, today)
                .map(date -> {
                    PaymentServiceImpl.DailyAggregate agg = byDate.get(date);
                    if (agg == null) {
                        // 지출 없으면 0원 DTO
                        return PaymentResponseDto.DailyTotal.builder()
                                .date(date)
                                .dayOfWeekKo(toKo(date.getDayOfWeek()))
                                .totalExpense(0L)
                                .transactionCount(0)
                                .build();
                    }
                    return toDailyTotal(agg);
                })
                .collect(Collectors.toList());

        return PaymentResponseDto.WeeklyDailyTotals.builder()
                .userId(userId)
                .from(monday)
                .to(today)
                .days(days)
                .build();
    }

    // 헬퍼: 요일 한글 변환
    private String toKo(DayOfWeek dow) {
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

    // 헬퍼: start~end 날짜 스트림
    private static Stream<LocalDate> datesBetweenInclusive(LocalDate start, LocalDate end) {
        long days = Duration.between(start.atStartOfDay(), end.plusDays(1).atStartOfDay()).toDays();
        return Stream.iterate(start, d -> d.plusDays(1)).limit(days);
    }

    public PaymentResponseDto.MonthlyTopCategories toMonthlyTop3Categories(
            Long userId, LocalDate from, LocalDate to, List<PaymentServiceImpl.CategoryAggregate> aggs) {

        long total = aggs.stream()
                .mapToLong(a -> a.getAmount() == null ? 0L : a.getAmount())
                .sum();

        AtomicInteger rank = new AtomicInteger(1);
        List<PaymentResponseDto.CategoryShare> top3 = aggs.stream()
                .limit(3) // 쿼리에서 이미 amount desc 정렬됨
                .map(a -> toCategoryShare(a, rank.getAndIncrement(), total))
                .collect(Collectors.toList());

        return PaymentResponseDto.MonthlyTopCategories.builder()
                .userId(userId)
                .from(from)
                .to(to)
                .totalExpense(total)
                .topCategories(top3)
                .build();
    }

    private PaymentResponseDto.CategoryShare toCategoryShare(
            PaymentServiceImpl.CategoryAggregate a, int rank, long total) {

        long amt = a.getAmount() == null ? 0L : a.getAmount();
        BigDecimal percentage = (total <= 0)
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(amt).multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);

        return PaymentResponseDto.CategoryShare.builder()
                .rank(rank)
                .categoryName(a.getCategoryName())
                .amount(amt)
                .transactionCount(a.getTransactionCount() == null ? 0 : a.getTransactionCount())
                .percentage(percentage)
                .build();
    }

}
