package com.wisespendinglife.wise_spending_life.domain.user.ownership.repository;

import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCharRepository extends JpaRepository<UserCharacter, Long> {

    /** 보유 여부 빠른 체크 */
    boolean existsByUser_IdAndCharacter_Id(Long userId, Long characterId);

    /** 유저 보유 캐릭터 목록 */
    List<UserCharacter> findByUser_IdOrderByIdDesc(Long userId);

    /** 특정 userId + characterId 단건 조회(필요시) */
    UserCharacter findByUser_IdAndCharacter_Id(Long userId, Long characterId);
}

