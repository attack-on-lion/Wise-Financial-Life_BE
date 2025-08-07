package com.wisespendinglife.wise_spending_life.domain.user.repository;

import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //유저 정보 조회
    Optional<UserEntity> findByIdAndIsDeletedFalse(Long id);

    //유저 정보 수정
    Optional<UserEntity> findById(Long id);
}
