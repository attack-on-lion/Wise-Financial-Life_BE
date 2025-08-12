package com.wisespendinglife.wise_spending_life.domain.gifticon.service;

import com.wisespendinglife.wise_spending_life.domain.gifticon.converter.GifticonConverter;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.repository.GifticonRepository;
import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;

import com.wisespendinglife.wise_spending_life.domain.store.repository.StoreRepository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class GifticonServiceImpl implements GifticonService {
    private static final int DEFAULT_SIZE = 10;
    private static final int MAX_SIZE = 30;

    private final GifticonRepository gifticonRepository;
    private final StoreRepository storeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GifticonResponseDTO> getAllGifticon(Timestamp lastCreatedAt, Long lastId, int size) {
        if (size <= 0 || size > MAX_SIZE) {
            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
        }
        if ((lastCreatedAt == null) != (lastId == null)) {
            throw new BusinessException(ErrorCode.INVALID_CURSOR);
        }

        List<GifticonEntity> entities = gifticonRepository.findSliceAll(lastCreatedAt, lastId, size);

        if (entities.isEmpty()) {
            if (lastCreatedAt == null && lastId == null) {
                throw new BusinessException(ErrorCode.GIFTICON_NOT_FOUND);
            }
            return java.util.Collections.emptyList();
        }

        List<GifticonResponseDTO> result = new ArrayList<>();
        for (GifticonEntity entity : entities) {
            result.add(GifticonConverter.togifticonResponseDTO(entity)); // 컨버터 메서드명과 일치
        }
        return result;
    }

    //기프티콘 스토어별 전체 조회(스크롤)
//    @Override
//    @Transactional(readOnly = true)
//    public List<GifticonResponseDTO> getGifticonsByStore(Long storeId, Timestamp lastCreatedAt, Long lastId, int size) {
//        if (size <= 0 || size > MAX_SIZE) {
//            throw new BusinessException(ErrorCode.INVALID_PAGE_SIZE);
//        }
//        if ((lastCreatedAt == null) != (lastId == null)) {
//            throw new BusinessException(ErrorCode.INVALID_CURSOR);
//        }
//
//        // 스토어 존재 여부 확인
//        boolean storeExists = storeRepository.existsById(storeId);
//        if (!storeExists) {
//            throw new BusinessException(ErrorCode.STORE_NOT_FOUND);
//        }
//
//        // 스토어별 커서 기반 조회
//        List<GifticonEntity> entities =
//                gifticonRepository.findSliceByStoreId(storeId, lastCreatedAt, lastId, size);
//
//        // Entity -> DTO 리스트 변환
//        List<GifticonResponseDTO> result = new java.util.ArrayList<>();
//        for (GifticonEntity e : entities) {
//            result.add(GifticonConverter.togifticonResponseDTO(e));
//        }
//        return result;
//    }

    //기프티콘 등록(Post)
    @Override
    @Transactional
    public Long createGifticon(Long storeId, GifticonRequestDTO requestDTO){
        StoreEntity store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

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

}
