package com.wisespendinglife.wise_spending_life.domain.user.ownership.converter;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userCharacter.UserCharResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserCharacter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserCharConverter {

    public UserCharResponseDto.OwnedCharacterDto toOwnedCharDto(UserCharacter entity) {
        return UserCharResponseDto.OwnedCharacterDto.builder()
                .characterId(entity.getCharacter().getId())
                .build();
    }

    /**
     * 단일 <-> 리스트 변환(now) <-> ListDto
     * 접근 단계: Private
     * @param entities
     * @return
     */
    private List<UserCharResponseDto.OwnedCharacterDto> toListOwnedDto(List<UserCharacter> entities) {
        return entities.stream()
                .map(this::toOwnedCharDto)
                .toList();
    }

    public UserCharResponseDto.OwnedCharacterListDto toOwnedListDto(List<UserCharacter> entities) {
        return UserCharResponseDto.OwnedCharacterListDto.builder()
                .ownedCharacters(toListOwnedDto(entities))
                .build();
    }


    public UserCharacter toEntity(User user, Character character) {
        return UserCharacter.builder()
                .user(user)
                .character(character)
                .build();
    }

    public UserCharResponseDto.PurchaseCharacterDto toPurchaseCharacterResponseDto(UserCharacter entity){
        return UserCharResponseDto.PurchaseCharacterDto.builder()
                .id(entity.getId())
                .build();
    }
}
