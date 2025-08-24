package com.wisespendinglife.wise_spending_life.domain.store.repository;
import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    boolean existsByStoreNameAndCategory_NameAndIsDeletedFalse(String storeName, String categoryName);

    //단건조회
    Optional<StoreEntity> findByIdAndIsDeletedFalse(Long id);

    List<StoreEntity> findAllByIsDeletedFalse();

    // 전체 조회 (삭제 제외, 가나다순 정렬, 커서 기반)
    @Query("""
        SELECT s
        FROM StoreEntity s
        WHERE s.isDeleted = false
          AND (
                s.storeName > :lastStoreName
                OR (s.storeName = :lastStoreName AND s.id > :lastId)
              )
        ORDER BY s.storeName ASC, s.id ASC
    """)
    List<StoreEntity> findAfterCursor(
            @Param("lastStoreName") String lastStoreName,
            @Param("lastId") Long lastId,
            Pageable pageable
    );
    boolean existsByIdAndIsDeletedFalse(Long id);

    /**
     * 카테고리로 필터한 전체 조회 (삭제 제외, 가나다순 정렬, 커서 기반)
     * - 정렬: storeName ASC, id ASC
     * - 커서: (lastStoreName, lastId) 둘 다 필요. 첫 페이지는 ("", 0L)로 호출.
     */
    @Query("""
        SELECT s
        FROM StoreEntity s
        WHERE s.isDeleted = false
          AND s.category.name = :categoryName
          AND (
                s.storeName > :lastStoreName
                OR (s.storeName = :lastStoreName AND s.id > :lastId)
              )
        ORDER BY s.storeName ASC, s.id ASC
    """)
    List<StoreEntity> findAfterCursorByCategory(
            String categoryName,
            String lastStoreName,
            Long lastId,
            Pageable pageable
    );

}
