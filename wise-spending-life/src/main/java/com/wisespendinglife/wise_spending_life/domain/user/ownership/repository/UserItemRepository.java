package com.wisespendinglife.wise_spending_life.domain.user.ownership.repository;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {

    List<UserItem> findByUser_IdOrderByIdDesc(Long userId);

}
