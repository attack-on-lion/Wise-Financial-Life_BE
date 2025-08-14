package com.wisespendinglife.wise_spending_life.domain.gifticon.service;

import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonListResponseDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface GifticonService {

    GifticonListResponseDTO getAllGifticon(LocalDateTime lastCreatedAt, Long lastId, int size); //기프티콘 전체 조회(스크롤)

    Long createGifticon(String storeName, GifticonRequestDTO requestDTO); //기프티콘 등록(Post)

    void deleteGifticon(Long gifticonId); //기프티콘 삭제
}
