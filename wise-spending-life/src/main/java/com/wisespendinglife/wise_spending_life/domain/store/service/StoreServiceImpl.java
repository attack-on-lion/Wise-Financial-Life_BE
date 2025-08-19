package com.wisespendinglife.wise_spending_life.domain.store.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.store.converter.StoreConverter;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.repository.StoreRepository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Store;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    //브랜드 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<StoreResponseDTO> getAllStores(){
        List<StoreResponseDTO> stores = storeRepository.findAll().stream()
                .filter(store -> Boolean.FALSE.equals(store.getIsDeleted()))
                .map(StoreConverter::toStoreResponseDTO)
                .toList();

        if (stores.isEmpty()) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }

        return stores;
    }


    //신규 브랜드 등록
    @Override
    @Transactional
    public Long createStore(String storeName, String logoUrl, Long categoryId) {
        // 카테고리 조회 (없으면 에러)
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        // 중복 체크
        if (storeRepository.existsByStoreNameAndCategory_IdAndIsDeletedFalse(storeName, categoryId)) {
            throw new BusinessException(ErrorCode.STORE_ALREADY_EXISTS);
        }

        // 엔티티 생성 후 저장
        StoreEntity entity = StoreEntity.builder()
                .storeName(storeName)
                .logoUrl(logoUrl)
                .category(category)
                .isDeleted(false)
                .build();

        return storeRepository.save(entity).getId();
    }


    //브랜드 삭제 (소프트 삭제)
    @Override
    @Transactional
    public void deleteStore(Long storeId) {
        StoreEntity entity = storeRepository.findByIdAndIsDeletedFalse(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        entity.setIsDeleted();
    }

    //브랜드명 중복 여부 체크
    @Override
    public boolean isStoreDuplicate(String storeName, Long categoryId) {
        return storeRepository.existsByStoreNameAndCategory_IdAndIsDeletedFalse(storeName, categoryId);
    }

    //카테고리 찾기 (없으면 생성)
    @Override
    @Transactional
    public Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(categoryName)
                            .type(CategoryType.STORE)
                            .build();
                    return categoryRepository.save(newCategory);
                });
    }
}

