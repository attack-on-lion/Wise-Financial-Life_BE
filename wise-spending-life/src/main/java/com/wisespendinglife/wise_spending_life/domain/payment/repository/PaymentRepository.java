package com.wisespendinglife.wise_spending_life.domain.payment.repository;

import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

}
