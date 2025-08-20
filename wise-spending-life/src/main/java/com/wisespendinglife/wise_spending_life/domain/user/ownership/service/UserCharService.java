package com.wisespendinglife.wise_spending_life.domain.user.ownership.service;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.character.service.CharacterService;
import com.wisespendinglife.wise_spending_life.domain.point.converter.PointConverter;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointRequestDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;
import com.wisespendinglife.wise_spending_life.domain.point.service.PointService;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.converter.UserCharConverter;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharRequestDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserCharacter;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.repository.UserCharRepository;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserReadService;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserCharService {

    private final UserCharRepository userCharRepository;
    private final UserCharConverter userCharConverter;
    private final UserReadService userReadService;
    private final CharacterService characterService;
    private final PointService pointService;
    private final PointConverter pointConverter;

    /**
     * 특정 유저가 보유한 캐릭터 목록을 반환
     * @param userId - 유저 아이디
     * @return - 보유 캐릭터 리스 반환
     */
    @Transactional(readOnly = true)
    public UserCharResponseDto.OwnedCharacterListDto getOwnedCharacters(Long userId) {

        List<UserCharacter> ownedCharacters = userCharRepository.findByUser_IdOrderByIdDesc(userId);

        return userCharConverter.toOwnedListDto(ownedCharacters);
    }

    /**
     * 유저 캐릭터 소유권 추가(구매)
     * @param dto - 결제 금액
     * @param userId - 유저 아이디
     * @param characterId - 구매하는 캐릭터 아이디
     * @return
     */
    public UserCharResponseDto.PurchaseCharacterDto addUserCharacter(
            UserCharRequestDto.PurchaseCharacterDto dto,
            Long userId,
            Long characterId
    ) {
        User user = userReadService.getEntity(userId);
        Character character = characterService.getEntity(characterId);

        UserCharacter entity = userCharConverter.toEntity(user, character);

        // 포인트 차감 DTO 로 변환
        PointRequestDto.SpendPointRequestDto pointSpendDto =
                pointConverter.toSpendPointRequestDto(-dto.getPrice(), SourceKind.purchase);

        // 포인트 차감
        pointService.handlePointChange(userId, pointSpendDto);

        // 캐릭터 소유권 추가
        UserCharacter save = userCharRepository.save(entity);

        return userCharConverter.toPurchaseCharacterResponseDto(save);
    }

    public boolean isOwned(Long userId, Long characterId) {
        boolean result = userCharRepository.existsByUser_IdAndCharacter_Id(userId, characterId);

        if (!result) {
            throw new BusinessException(ErrorCode.INVALID_CHARACTER_REQUEST);
        }

        return result;
    }

}
