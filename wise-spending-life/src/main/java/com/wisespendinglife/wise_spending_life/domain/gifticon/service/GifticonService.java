package com.wisespendinglife.wise_spending_life.domain.gifticon.service;

import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import java.util.List;
import java.sql.Timestamp;

public interface GifticonService {

    List<GifticonResponseDTO> getAllGifticon(Timestamp lastCreatedAt, Long lastId, int size); //기프티콘 전체 조회(스크롤)

//    List<GifticonResponseDTO> getGifticonsByStore(Long storeId, Timestamp lastCreatedAt, Long lastId, int size); //기프티콘 스토어별 전체 조회(스크롤)

    Long createGifticon(Long storeId, GifticonRequestDTO requestDTO); //기프티콘 등록(Post)

    void deleteGifticon(Long gifticonId); //기프티콘 삭제
}
