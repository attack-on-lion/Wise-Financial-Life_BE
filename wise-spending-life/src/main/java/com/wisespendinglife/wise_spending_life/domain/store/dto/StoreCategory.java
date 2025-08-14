package com.wisespendinglife.wise_spending_life.domain.store.dto;

public enum StoreCategory {
    RESTAURANT("음식점"),
    CAFE("카페"),
    CONVENIENCE_STORE("편의점"),
    MART("마트"),
    ETC("기타");

    private final String label;

    StoreCategory(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }

}
