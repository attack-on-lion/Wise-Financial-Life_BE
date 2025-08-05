package com.wisespendinglife.wise_spending_life.domain.payment.repository;

import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    /**
     * Payment Date Range 조회.
     * @param from
     * @param to
     * @param pageable
     * @return
     */
    Page<Payment> findByTransactionAtBetween(
            LocalDate from,
            LocalDate to,
            Pageable pageable);
}
