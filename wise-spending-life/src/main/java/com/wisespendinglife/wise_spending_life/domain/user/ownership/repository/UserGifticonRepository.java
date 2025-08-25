package com.wisespendinglife.wise_spending_life.domain.user.ownership.repository;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserGifticon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGifticonRepository extends JpaRepository<UserGifticon, Long> {
    List<UserGifticon> findByUser_Id(Long userId);
    Optional<UserGifticon> findByUser_IdAndGifticon_Id(Long userId, Long gifticonId);
    Optional<UserGifticon> findByIdAndUser_Id(Long id, Long userId);
    Optional<UserGifticon> findByGifticon_Id(Long gifticonId);
    boolean existsByUser_IdAndGifticon_Id(Long userId, Long gifticonId);
}
