package com.wisespendinglife.wise_spending_life.domain.payment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

/*
 * PaymentResponseDto
 *
 * @author holychann
 * @since 2025-08-05
 */
public class PaymentResponseDto {

    /**
     * 주간(월→오늘) 일자별 총 지출 응답
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeeklyDailyTotals {
        private Long userId;

        // 응답 범위 메타데이터 (월요일~오늘)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate from;   // 이번 주 월요일

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate to;     // 오늘

        private List<DailyTotal> days; // 월~오늘까지 날짜순 정렬
    }

    /**
     * 특정 일자의 총 지출 합계
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyTotal {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate date;  // 해당 일자

        private String dayOfWeekKo;  // "월","화","수","목","금","토","일" (프론트 편의)

        private Long totalExpense;  // 해당 일자 총 지출(원). 없으면 0
        private Integer transactionCount;  // 선택: 해당 일자의 지출 건수(없으면 0)
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class PaymentCreateResponseDto {
        private Long id;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class Payments {
        private Summary summary;
        private PageInfo pageInfo;
        private List<Item> items;
    }

    /**
     * summary
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Summary {
        private Period period;
        private long totalExpense;   // 지출 합계
        private long totalIncome;    // 수입 합계
        private int  count;          // 거래 건수
    }


    /**
     * period
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Period {
        private LocalDate from;      // 2025-07-01
        private LocalDate to;        // 2025-07-31
    }


    /**
     * pageInfo
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PageInfo {
        private int  page;  // 0-based
        private int  size;  // 페이지당 row 수
        private boolean hasNext;  // 다음 페이지 존재 여부
    }


    /**
     * item
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private Long id;
        private LocalDateTime transactionAt; // format: "2025-07-09T15:12:00+09:00"
        private String storeName;
        private long amount;
        private PaymentDirection direction;
        private PaymentMethod method;
        private Category category;
    }


    /**
     * category
     */
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category {
        private Long id;
        private String name;
    }

    /**
     * 이번달 TOP N 카테고리 응답
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MonthlyTopCategories {
        private Long userId;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate from;  // 이번 달 1일

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate to;  // 오늘

        private Long totalExpense;  // 월 총 지출(원) - 퍼센트 계산 분모

        private List<CategoryShare> topCategories;  // 금액 내림차순 상위 N개 (기본 3)
    }

    /**
     * 카테고리별 점유율(%) 항목
     */
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryShare {
        private Integer rank;  // 1,2,3
        private String categoryName;  // 카테고리 명 (필요 시 한글명)

        private Long amount;  // 해당 카테고리 총 지출(원)
        private Integer transactionCount;  // 해당 카테고리 지출 건수

        private BigDecimal percentage;  // 월 총 지출 대비 점유율(%) 소수점 2자리
    }

}
