package com.wisespendinglife.wise_spending_life.domain.point.repository;

import com.wisespendinglife.wise_spending_life.domain.point.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointLedgerRepository extends JpaRepository<Point, Long> {
}
