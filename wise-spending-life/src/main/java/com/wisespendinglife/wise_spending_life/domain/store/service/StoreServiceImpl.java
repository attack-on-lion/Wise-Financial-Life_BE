//package com.wisespendinglife.wise_spending_life.domain.store.service;
//
//import com.wisespendinglife.wise_spending_life.domain.store.converter.StoreConverter;
//import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
//import com.wisespendinglife.wise_spending_life.domain.store.repository.StoreRepository;
//import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
//import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
//import org.apache.catalina.Store;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class StoreServiceImpl {
//    private final StoreRepository storeRepository;
//
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<StoreResponseDTO> getAllStores(){
//        List<Store> stores = storeRepository.findAll()
//                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
//
//        return StoreConverter.toStoreResponseDTO(store);
//    }
//
//    // 제휴 상점 리스트에 개별 기프티콘 등록
//    void addGifticonToStore(Long storeId, Long gifticonId);
//
//    // 제휴 상점 리스트에서 개별 기프티콘 삭제
//    void removeGifticonFromStore(Long storeId, Long gifticonId);
//
//
//
//}
