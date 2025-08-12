package com.wisespendinglife.wise_spending_life.domain.gifticon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wisespendinglife.wise_spending_life.domain.gifticon.entity.GifticonEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface GifticonRepository extends JpaRepository<GifticonEntity, Long> {
    //전체 조회 (스크롤기반)
    @Query(value = """
    SELECT *
    FROM gifticon g
    WHERE g.is_deleted = false
      AND (
        CAST(:lastCreatedAt AS timestamp) IS NULL
        OR g.created_at < CAST(:lastCreatedAt AS timestamp)
        OR (g.created_at = CAST(:lastCreatedAt AS timestamp) AND g.id < CAST(:lastId AS bigint))
      )
    ORDER BY g.created_at DESC, g.id DESC
    LIMIT :size
    """, nativeQuery = true)
    List<GifticonEntity> findSliceAll(
            @Param("lastCreatedAt") Timestamp lastCreatedAt,
            @Param("lastId") Long lastId,
            @Param("size") Integer size
    );


//    //기프티콘 스토어별 조회 (스크롤기반)
//    @Query(value = """
//    SELECT *
//    FROM gifticon g
//    WHERE g.is_deleted = false
//      AND g.store_id = :storeId
//      AND (
//        CAST(:lastCreatedAt AS timestamp) IS NULL
//        OR g.created_at < CAST(:lastCreatedAt AS timestamp)
//        OR (g.created_at = CAST(:lastCreatedAt AS timestamp) AND g.id < CAST(:lastId AS bigint))
//      )
//    ORDER BY g.created_at DESC, g.id DESC
//    LIMIT :size
//    """, nativeQuery = true)
//    List<GifticonEntity> findSliceByStoreId(
//            @Param("storeId") Long storeId,
//            @Param("lastCreatedAt") Timestamp lastCreatedAt,
//            @Param("lastId") Long lastId,
//            @Param("size") Integer size
//    );

    //기프티콘 단건 조회
    Optional<GifticonEntity> findByIdAndIsDeletedFalse(Long id);

    //존재 여부 체크 (업데이트/삭제 전 검증용)
    Boolean existsByIdAndIsDeletedFalse(Long id);

}
