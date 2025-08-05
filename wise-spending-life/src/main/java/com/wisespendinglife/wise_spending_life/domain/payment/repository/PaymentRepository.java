package com.wisespendinglife.wise_spending_life.domain.payment.repository;

import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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
    @Query("SELECT p FROM Payment p " +
            "WHERE function('date', p.transactionAt) BETWEEN :from AND :to")
    Page<Payment> findByTransactionAtBetween(
            LocalDate from,
            LocalDate to,
            Pageable pageable);
}
