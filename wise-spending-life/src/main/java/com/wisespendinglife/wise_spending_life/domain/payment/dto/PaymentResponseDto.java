package com.wisespendinglife.wise_spending_life.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
