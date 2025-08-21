package com.wisespendinglife.wise_spending_life.domain.notification.repository;

import com.wisespendinglife.wise_spending_life.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<Notification, Long> {
    /** 유저별 최신순 페이지 조회 (삭제 안 된 것만) */
    Page<Notification> findByUser_IdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId, Pageable pageable);

    /** 유저별 카테고리별 최신순 페이지 조회 (삭제 안 된 것만) */
    Page<Notification> findByUser_IdAndCategory_IdAndIsDeletedFalseOrderByCreatedAtDesc(
            Long userId, Long categoryId, Pageable pageable
    );

    /** 유저별 단건 알림 조회 (삭제 안 된 것만) */
    Optional<Notification> findByIdAndUser_IdAndIsDeletedFalse(Long id, Long userId);



}
