package com.wisespendinglife.wise_spending_life.domain.composite.repository;

import com.wisespendinglife.wise_spending_life.domain.composite.entity.Composite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositeRepository extends JpaRepository<Composite, Long> {
}
