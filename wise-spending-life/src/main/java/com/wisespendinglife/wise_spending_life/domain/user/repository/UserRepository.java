package com.wisespendinglife.wise_spending_life.domain.user.repository;

import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //유저 정보 조회 및 수정
    Optional<User> findByIdAndIsDeletedFalse(Long id);
}
