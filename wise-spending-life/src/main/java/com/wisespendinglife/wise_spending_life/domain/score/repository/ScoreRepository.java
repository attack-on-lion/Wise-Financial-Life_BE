package com.wisespendinglife.wise_spending_life.domain.score.repository;

import com.wisespendinglife.wise_spending_life.domain.score.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findFirstByUser_IdOrderByCreatedAtDesc(Long userId);

    @Query("""
        SELECT s FROM Score s
        WHERE s.user.id = :userId
          AND s.createdAt BETWEEN :start AND :end
        """)
    Optional<Score> findMonthlyByUserId(
            @Param("userId") Long userId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );
}
