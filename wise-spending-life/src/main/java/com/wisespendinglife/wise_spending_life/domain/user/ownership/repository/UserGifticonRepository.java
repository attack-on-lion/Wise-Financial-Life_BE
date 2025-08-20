package com.wisespendinglife.wise_spending_life.domain.user.ownership.repository;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserGifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGifticonRepository extends JpaRepository<UserGifticon, Long> {
    List<UserGifticon> findByUserId(Long userId);
    Optional<UserGifticon> findByUserIdAndGifticonId(Long userId, Long gifticonId);
}
