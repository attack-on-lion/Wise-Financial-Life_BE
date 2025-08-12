package com.wisespendinglife.wise_spending_life.domain.store.repository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long>{
    // 전체 목록 조회 : 등록 순서(id 내림차순)
    List<StoreEntity> findAllByIsDeletedFalseOrderByIdDesc();

}
