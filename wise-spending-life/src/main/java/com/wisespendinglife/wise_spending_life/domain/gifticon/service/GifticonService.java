package com.wisespendinglife.wise_spending_life.domain.gifticon.service;

import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonListResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface GifticonService {

     //기프티콘 전체 조회(스크롤)
    GifticonListResponseDTO getAllGifticon(String lastStoreName, String lastGifticonName, Long lastId, int size);

    Long createGifticon(String storeName, GifticonRequestDTO requestDTO); //기프티콘 등록(Post)

    void deleteGifticon(Long gifticonId); //기프티콘 삭제

    GifticonListResponseDTO getGifticonsByStore(Long storeId, LocalDateTime lastCreatedAt, Long lastId, int size); //스토어별 기프티콘 조회

    GifticonEntity getEntity(Long gifticonId);
}
