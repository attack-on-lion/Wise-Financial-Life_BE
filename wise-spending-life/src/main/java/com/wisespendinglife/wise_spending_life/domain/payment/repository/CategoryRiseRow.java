package com.wisespendinglife.wise_spending_life.domain.payment.repository;

public interface CategoryRiseRow {
    Long getCategoryId();
    String getCategoryName();
    Long getPrevTotal();
    Long getCurrTotal();
}
