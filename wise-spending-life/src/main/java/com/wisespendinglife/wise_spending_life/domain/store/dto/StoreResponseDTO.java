package com.wisespendinglife.wise_spending_life.domain.store.dto;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null 필드 응답에서 숨기기 위함.
public class StoreResponseDTO {
    private Long storeId;
    private String storeName; //상점 이름
    private String logoUrl; //브랜드 이미지

    private Long categoryId; //카테고리 FK
    private String categoryName; //카테고리 등록 위함

    public StoreResponseDTO(Long storeId, String storeName, String logoUrl,
                             Long categoryId, String categoryName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.logoUrl = logoUrl;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // 브랜드용 3개 (브랜드 카드에 필요한 최소 정보)
    public StoreResponseDTO(Long storeId, String storeName, String logoUrl) {
        this(storeId, storeName, logoUrl, null,null);
    }

    // 풀 정보 (전체 리스트 조회 등에 쓰임)
    @Builder
    public static StoreResponseDTO of(Long storeId, String storeName, String logoUrl,
                                      Long categoryId, StoreCategory category) {
        return new StoreResponseDTO(storeId, storeName, logoUrl, categoryId, category != null ? category.name() : null);
    }
}
