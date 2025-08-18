package com.wisespendinglife.wise_spending_life.domain.item.repository;

import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
