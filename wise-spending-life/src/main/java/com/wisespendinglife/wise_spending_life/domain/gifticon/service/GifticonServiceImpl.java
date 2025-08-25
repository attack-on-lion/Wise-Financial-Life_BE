package com.wisespendinglife.wise_spending_life.domain.gifticon.service;

import com.wisespendinglife.wise_spending_life.domain.gifticon.converter.GifticonConverter;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.repository.GifticonRepository;
import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonListResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.repository.StoreRepository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class GifticonServiceImpl implements GifticonService {
    private static final int DEFAULT_SIZE = 9;
    private static final int MAX_SIZE = 10;

    private final GifticonRepository gifticonRepository;
    private final StoreRepository storeRepository;

    @Override
    public GifticonListResponseDTO getAllGifticon(String lastStoreName, String lastGifticonName, Long lastId, int size) {
        if (size <= 0 || size > MAX_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
        }
        if ((lastStoreName == null || lastGifticonName == null) != (lastId == null)) {
            throw new BusinessException(ErrorCode.INVALID_CURSOR);
        }

        final boolean firstPage = (lastStoreName == null && lastGifticonName == null && lastId == null);
        if (firstPage) {
            // 가나다 정렬이므로 가장 작은 값으로 초기화
            lastStoreName = "";
            lastGifticonName = "";
            lastId = 0L;
        }

        Pageable pageable = PageRequest.of(0, size + 1);
        List<GifticonEntity> entities = gifticonRepository.findAfterCursorByStoreNameAndGifticonName(
                lastStoreName, lastGifticonName, lastId, pageable
        );

        if (entities.isEmpty() && firstPage) {
            throw new BusinessException(ErrorCode.GIFTICON_NOT_FOUND);
        }

        boolean hasNext = entities.size() > size;
        if (hasNext) {
            entities = entities.subList(0, size);
        }

//        var items = entities.stream()
//                .map(GifticonConverter::togifticonResponseDTO)
//                .toList();

        GifticonListResponseDTO.Cursor cursor = null;
        if (!entities.isEmpty()) {
            GifticonEntity lastEntity = entities.get(entities.size() - 1);
            cursor = GifticonListResponseDTO.Cursor.builder()
                    .lastId(lastEntity.getId())
                    .lastStoreName(lastEntity.getStore().getStoreName()) //브랜드명
                    .lastGifticonName(lastEntity.getName()) //기프티콘 이름
                    .build();
        }

        var items = entities.stream()
                .map(GifticonConverter::togifticonResponseDTO)
                .toList();

        return GifticonListResponseDTO.builder()
                .gifticonlist(items)
                .nextCursor(cursor)
                .hasNext(hasNext)
                .size(size)
                .build();
    }

    //기프티콘 등록(Post)
    @Override
    public Long createGifticon(String storeName, GifticonRequestDTO requestDTO) {
        if (storeName == null || storeName.trim().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_STORE_NAME);
        }
        final String normalized = storeName.trim();

        // 스토어 이름 조회 -> 없으면 예외
        StoreEntity store = storeRepository.findByStoreName(normalized)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        // DTO → 엔티티 변환
        GifticonEntity gifticon = GifticonConverter.toEntity(requestDTO, store);

        if (gifticon.getIsRecommend() == null) gifticon.updateIsRecommend(false);
        if (gifticon.getIsDeleted()   == null) gifticon.updateIsDeleted(false);

        gifticonRepository.save(gifticon);
        return gifticon.getId();
    }


    //기프티콘 삭제
    @Override
    public void deleteGifticon(Long gifticonId) {
        GifticonEntity gifticon = gifticonRepository.findByIdAndIsDeletedFalse(gifticonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GIFTICON_NOT_FOUND));

        // 소프트 삭제 처리
        gifticon.updateIsDeleted(true);
    }

    //브랜드별 기프티콘 조회
    @Override
    public GifticonListResponseDTO getGifticonsByStore(
            Long storeId,
            LocalDateTime lastCreatedAt,
            Long lastId,
            int size
    ) {
        if (size <= 0 || size > MAX_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
        }
        if ((lastCreatedAt == null) != (lastId == null)) {
            throw new BusinessException(ErrorCode.INVALID_CURSOR);
        }
        if (!storeRepository.existsByIdAndIsDeletedFalse(storeId)) {
            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
        }

        final boolean firstPage = (lastCreatedAt == null && lastId == null);
        if (firstPage) {
            lastCreatedAt = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
            lastId = Long.MAX_VALUE;
        }

        // size+1 로 조회 후 hasNext 계산
        Pageable pageable = PageRequest.of(0, size + 1);
        List<GifticonEntity> entities = gifticonRepository
                .findByStoreIdAfterCursor(storeId, lastCreatedAt, lastId, pageable);

        boolean hasNext = entities.size() > size;
        if (hasNext) {
            entities = entities.subList(0, size); // 마지막 1개 제거
        }

        GifticonListResponseDTO.Cursor cursor = null;
        if (!entities.isEmpty()) {
            GifticonEntity lastItem = entities.get(entities.size() - 1);
            cursor = GifticonListResponseDTO.Cursor.builder()
                    .lastId(lastItem.getId())
                    .lastStoreName(lastItem.getStore().getStoreName()) //브랜드명
                    .lastGifticonName(lastItem.getName()) //기프티콘 이름
                    .build();
        }

        var items = entities.stream()
                .map(GifticonConverter::togifticonResponseDTO)
                .toList();

        return GifticonListResponseDTO.builder()
                .gifticonlist(items)
                .nextCursor(cursor)   // null이면 프론트는 더 불러오지 않으면 됨
                .hasNext(hasNext)
                .size(size)
                .build();
    }

    @Override
    public GifticonEntity getEntity(Long gifticonId) {

        GifticonEntity entity = gifticonRepository.findByIdAndIsDeletedFalse(gifticonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GIFTICON_NOT_FOUND));

        return entity;
    }
}
