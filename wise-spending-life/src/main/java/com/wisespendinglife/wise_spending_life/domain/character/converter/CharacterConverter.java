package com.wisespendinglife.wise_spending_life.domain.character.converter;

import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class CharacterConverter {

    public CharacterResponseDto.CharacterCreateDto toCreateResponseDto(Character character) {
        return CharacterResponseDto.CharacterCreateDto.builder()
                .id(character.getId())
                .build();
    }

    public Character toEntity(CharacterRequestDto.CreateCharacterDto dto) {
        return Character.builder()
                .name(dto.getName())
                .code(dto.getCode())
                .imageUrl(dto.getImageUrl())
                .price(dto.getPrice())
                .build();
    }

    public CharacterResponseDto.Item toItemDto(Character entity) {
        return CharacterResponseDto.Item.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .imageUrl(entity.getImageUrl())
                .price(entity.getPrice())
                .build();
    }

    public List<CharacterResponseDto.Item> toItemDtos(List<Character> entities) {
        return  entities.stream()
                .map(this::toItemDto)
                .toList();
    }
}
