package com.wisespendinglife.wise_spending_life.domain.character.service;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;


public interface CharacterService {
    CharacterResponseDto.Characters getCharacters(int currenPage, int size);
    CharacterResponseDto.CharacterCreateDto saveCharacter(CharacterRequestDto.CreateCharacterDto character);
    Character getEntity(Long characterId);
}
