package com.wisespendinglife.wise_spending_life.domain.store.service;

import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;

import java.util.List;

public interface StoreService {
    //브랜드 전체 조회
    List<StoreResponseDTO> getAllStores();

    //신규 브랜드 등록
    Long createStore(String storeName, String logoUrl, Long categoryId);

    //브랜드 삭제
    void deleteStore(Long storeId);

    //브랜드명 중복 여부 체크
    boolean isStoreDuplicate(String storeName, Long categoryId);

    //카테고리 찾기
    Category getOrCreateCategory(String categoryName);

}
