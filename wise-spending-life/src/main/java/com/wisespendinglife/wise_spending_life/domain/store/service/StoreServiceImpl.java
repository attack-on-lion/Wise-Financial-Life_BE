package com.wisespendinglife.wise_spending_life.domain.store.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.store.converter.StoreConverter;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreListResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.repository.StoreRepository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    final int MAX_SIZE = 10;

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    //브랜드 전체 조회
    @Override
    @Transactional(readOnly = true)
    public StoreListResponseDTO getAllStores(String lastStoreName, Long lastId, int size) {
        if (size <= 0 || size > MAX_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
        }
        // 커서 파라미터는 둘 다 null 이거나, 둘 다 값이 있어야 함
        if ((lastStoreName == null) != (lastId == null)) {
            throw new BusinessException(ErrorCode.INVALID_CURSOR);
        }

        final boolean firstPage = (lastStoreName == null && lastId == null);
        if (firstPage) {
            // 가나다순 ASC 이므로, "처음부터" 가져오려면 가장 작은 커서를 준다.
            // 공백/빈 문자열 상호주의: 실제 저장 시 trim 해두셨으면 ""이면 충분합니다.
            lastStoreName = "";
            lastId = 0L;
        }

        Pageable pageable = PageRequest.of(0, size + 1); // size+1로 더 가져와서 다음 페이지 여부 판단
        List<StoreEntity> entities = storeRepository.findAfterCursor(lastStoreName, lastId, pageable);

        if (entities.isEmpty() && firstPage) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }

        boolean hasNext = entities.size() > size;
        if (hasNext) {
            entities = entities.subList(0, size);
        }

        var items = entities.stream()
                .map(StoreConverter::toStoreResponseDTO)
                .toList();

        StoreListResponseDTO.Cursor cursor = null;
        if (!items.isEmpty()) {
            var last = items.get(items.size() - 1);
            cursor = StoreListResponseDTO.Cursor.builder()
                    .lastStoreName(last.getStoreName())
                    .lastId(last.getStoreId())
                    .build();
        }

        return StoreListResponseDTO.builder()
                .stores(items)
                .nextCursor(cursor) // null이면 프론트는 더 불러오지 않으면 됨
                .hasNext(hasNext)
                .size(size)
                .build();
    }

    /**
     * 카테고리로 필터된 브랜드 조회 (커서 기반)
     * 첫 호출은 lastStoreName/lastId 없이 호출하면 되고,
     * 내부에서 ("", 0L)로 초기화해서 가나다(ASC) + id(ASC) 기준으로 첫 페이지를 반환합니다.
     */
    @Transactional(readOnly = true)
    public StoreListResponseDTO getStoresByCategory(String categoryName, String lastStoreName, Long lastId, int size) {
        // size 검증
        if (size <= 0 || size > MAX_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
        }

        // 커서는 둘 다 null 이거나 둘 다 값이 있어야 함
        if ((lastStoreName == null) != (lastId == null)) {
            throw new BusinessException(ErrorCode.INVALID_CURSOR);
        }

        // 카테고리 존재 여부 확인 (없으면 예외)
        String normalizedCategory = categoryName == null ? null : categoryName.trim();
        categoryRepository.findByNameIgnoreCase(normalizedCategory)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        final boolean firstPage = (lastStoreName == null && lastId == null);
        if (firstPage) {
            // 가나다순 ASC의 시작 커서
            lastStoreName = "";
            lastId = 0L;
        }

        Pageable pageable = PageRequest.of(0, size + 1);
        List<StoreEntity> entities = storeRepository.findAfterCursorByCategory(
                normalizedCategory, lastStoreName, lastId, pageable
        );

        if (entities.isEmpty() && firstPage) {
            // 해당 카테고리에 등록된 브랜드가 하나도 없을 때
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }

        boolean hasNext = entities.size() > size;
        if (hasNext) {
            entities = entities.subList(0, size);
        }

        var items = entities.stream()
                .map(StoreConverter::toStoreResponseDTO)
                .toList();

        StoreListResponseDTO.Cursor cursor = null;
        if (!items.isEmpty()) {
            var last = items.get(items.size() - 1);
            cursor = StoreListResponseDTO.Cursor.builder()
                    .lastStoreName(last.getStoreName())
                    .lastId(last.getStoreId())
                    .build();
        }

        return StoreListResponseDTO.builder()
                .stores(items)
                .nextCursor(cursor)
                .hasNext(hasNext)
                .size(size)
                .build();
    }


    //신규 브랜드 등록
    @Override
    @Transactional
    public Long createStore(StoreRequestDTO request) {
        String storeName = request.getStoreName() != null ? request.getStoreName().trim() : null;
        String logoUrl = request.getLogoUrl()   != null ? request.getLogoUrl().trim()   : null;
        String categoryName = request.getCategoryName() != null ? request.getCategoryName().trim() : null;

        // 카테고리 조회 (없으면 에러)
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));

        // 중복 체크
        if (storeRepository.existsByStoreNameAndCategory_NameAndIsDeletedFalse(storeName, category.getName())) {
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
    public boolean isStoreDuplicate(String storeName, String categoryName) {
        return storeRepository.existsByStoreNameAndCategory_NameAndIsDeletedFalse(storeName, categoryName);
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

