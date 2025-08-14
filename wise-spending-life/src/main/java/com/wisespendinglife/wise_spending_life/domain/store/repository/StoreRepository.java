package com.wisespendinglife.wise_spending_life.domain.store.repository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long>{
    // 전체 목록 조회 : 등록 순서(id 내림차순)
    List<StoreEntity> findAllByIsDeletedFalseOrderByIdDesc();

    //기프티콘에서 스토어 이름 받아올 때 사용
    Optional<StoreEntity> findByStoreName(String storeName);

    //DB에 스토어 이름이 존재하는지 확인용
    boolean existsByStoreName(String storeName);
}
