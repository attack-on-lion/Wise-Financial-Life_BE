package com.wisespendinglife.wise_spending_life.domain.store.service;

import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreListResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;

import java.util.List;

public interface StoreService {
    //브랜드 전체 조회
    //List<StoreResponseDTO> getAllStores();
    StoreListResponseDTO getAllStores(String lastStoreName, Long lastId, int size);

    //신규 브랜드 등록
    Long createStore(StoreRequestDTO request);

    //브랜드 삭제
    void deleteStore(Long storeId);

    //브랜드명 중복 여부 체크
    boolean isStoreDuplicate(String storeName, String categoryName);

    //카테고리 찾기
    Category getOrCreateCategory(String categoryName);

}
