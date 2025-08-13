package com.wisespendinglife.wise_spending_life.domain.gifticon.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GifticonRepository extends JpaRepository<GifticonEntity, Long> {
    //전체 조회 (스크롤기반)
    @Query("""
        SELECT g
        FROM GifticonEntity g
        WHERE g.isDeleted = false
          AND (
                g.createdAt < :lastCreatedAt
                OR (g.createdAt = :lastCreatedAt AND g.id < :lastId)
              )
        ORDER BY g.createdAt DESC, g.id DESC
    """)
    List<GifticonEntity> findAfterCursor(
            @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
            @Param("lastId") Long lastId,
            Pageable pageable
    );

    //기프티콘 단건 조회
    Optional<GifticonEntity> findByIdAndIsDeletedFalse(Long id);

    //존재 여부 체크 (업데이트/삭제 전 검증용)
    Boolean existsByIdAndIsDeletedFalse(Long id);

}
