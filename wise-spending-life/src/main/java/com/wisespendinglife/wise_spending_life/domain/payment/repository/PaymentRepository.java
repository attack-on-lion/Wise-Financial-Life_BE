package com.wisespendinglife.wise_spending_life.domain.payment.repository;

import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import com.wisespendinglife.wise_spending_life.domain.score.dto.CategoryState;
import com.wisespendinglife.wise_spending_life.domain.score.dto.MonthlyState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * Payment Date Range 조회.
     *
     * @param from 조회 시작 날짜
     * @param to 조회 종료 날짜
     * @param pageable 페이징 정보
     * @return
     */
    Page<Payment> findByTransactionAtBetween(
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);

    Page<Payment> findByCategory_NameIgnoreCaseAndTransactionAtBetween(
            String category,             // 카테고리 이름
            LocalDateTime from,
            LocalDateTime to,
            Pageable pageable);



    /**
     * 총 수입 및 총 지출 금액
     * @param start
     * @param end
     * @return
     */
    @Query("""
            SELECT new com.wisespendinglife.wise_spending_life.domain.score.dto.MonthlyState(
                SUM(CASE WHEN p.direction = 'INFLOW' THEN p.amount ELSE 0 END),
                SUM(CASE WHEN p.direction = 'OUTFLOW' THEN p.amount ELSE 0 END),
                NULL
            )
            FROM Payment p
            WHERE p.transactionAt BETWEEN :start AND :end
        """)
    MonthlyState findIncomeAndOutflow(LocalDateTime start,
                                      LocalDateTime end);


    /**
     * 카테고리별 총 수입 및 총 지출 금액
     *
     * @param start
     * @param end
     * @return
     */
    @Query("""
        SELECT new com.wisespendinglife.wise_spending_life.domain.score.dto.CategoryState(
            c.name,
            SUM(p.amount),
            COUNT(p)
        )
        FROM Payment p
        JOIN p.category c
        WHERE p.direction = 'OUTFLOW'
          AND p.transactionAt BETWEEN :start AND :end
        GROUP BY c.name
    """)
    List<CategoryState> findCategoryStats(LocalDateTime start,
                                          LocalDateTime end);
}
