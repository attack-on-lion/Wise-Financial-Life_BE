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

public class GifticonServiceImpl implements GifticonService {
    private static final int DEFAULT_SIZE = 9;
    private static final int MAX_SIZE = 10;

    private final GifticonRepository gifticonRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true)
    public GifticonListResponseDTO getAllGifticon(LocalDateTime lastCreatedAt, Long lastId, int size) {
        if (size <= 0 || size > MAX_SIZE) {throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);}
        if ((lastCreatedAt == null) != (lastId == null)) {throw new BusinessException(ErrorCode.INVALID_CURSOR);}

        final boolean firstPage = (lastCreatedAt == null && lastId == null);
        if (firstPage) {
            lastCreatedAt = LocalDateTime.of(9999, 12, 31, 23, 59, 59);
            lastId = Long.MAX_VALUE;
        }

        // size+1 로 조회
        // 가져온 리스트가 size+1 이면 마지막 하나를 잘라내고, 그 마지막 요소의 (createdAt, id)를 next로 보냄.
        Pageable pageable = PageRequest.of(0, size + 1);
        List<GifticonEntity> entities = gifticonRepository.findAfterCursor(lastCreatedAt, lastId, pageable);

        if (entities.isEmpty() && firstPage) {
            throw new BusinessException(ErrorCode.GIFTICON_NOT_FOUND);
        }

        boolean hasNext = entities.size() > size;
        if (hasNext) {
            entities = entities.subList(0, size); // 마지막 1개 제거
        }


        var items = entities.stream().map(GifticonConverter::togifticonResponseDTO).toList();

        GifticonListResponseDTO.Cursor cursor = null;
        if (!items.isEmpty()) {
            //GifticonResponseDTO last = items.get(items.size() - 1);
            var last = items.get(items.size() - 1);
            cursor = GifticonListResponseDTO.Cursor.builder()
                    .lastId(last.getId())
                    .lastCreatedAt(last.getCreatedAt())
                    .build();
        }

        return GifticonListResponseDTO.builder()
                .gifticonlist(items)
                .nextCursor(cursor)  // null 이면 프론트는 더 불러오지 않으면 됨
                .hasNext(hasNext)
                .size(size)
                .build();
    }

    //기프티콘 등록(Post)
    @Override
    @Transactional
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
    @Transactional
    public void deleteGifticon(Long gifticonId) {
        GifticonEntity gifticon = gifticonRepository.findByIdAndIsDeletedFalse(gifticonId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GIFTICON_NOT_FOUND));

        // 소프트 삭제 처리
        gifticon.updateIsDeleted(true);
    }

    //브랜드별 기프티콘 조회
    @Override
    @Transactional(readOnly = true)
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

        var items = entities.stream()
                .map(GifticonConverter::togifticonResponseDTO)
                .toList();

        GifticonListResponseDTO.Cursor cursor = null;
        if (!items.isEmpty()) {
            var lastItem = items.get(items.size() - 1);
            cursor = GifticonListResponseDTO.Cursor.builder()
                    .lastId(lastItem.getId())
                    .lastCreatedAt(lastItem.getCreatedAt())
                    .build();
        }

        return GifticonListResponseDTO.builder()
                .gifticonlist(items)
                .nextCursor(cursor)   // null이면 프론트는 더 불러오지 않으면 됨
                .hasNext(hasNext)
                .size(size)
                .build();
    }

}
