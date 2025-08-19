package com.wisespendinglife.wise_spending_life.domain.store.repository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long>{
    //스토어 이름 조회
    Optional<StoreEntity> findByStoreName(String storeName);

    // 삭제 제외 버전 (권장)
    Optional<StoreEntity> findByStoreNameAndIsDeletedFalse(String storeName);

    //중복 생성 사전 체크
    boolean existsByStoreNameAndCategory_IdAndIsDeletedFalse(String storeName, Long categoryId);

    //단건조회
    Optional<StoreEntity> findByIdAndIsDeletedFalse(Long id);

}
