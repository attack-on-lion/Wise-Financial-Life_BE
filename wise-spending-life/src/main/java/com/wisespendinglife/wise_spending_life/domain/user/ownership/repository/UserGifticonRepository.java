package com.wisespendinglife.wise_spending_life.domain.user.ownership.repository;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserGifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGifticonRepository extends JpaRepository<UserGifticon, Long> {
}
