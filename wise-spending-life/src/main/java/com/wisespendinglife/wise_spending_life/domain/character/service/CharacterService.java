package com.wisespendinglife.wise_spending_life.domain.character.service;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;


public interface CharacterService {
    CharacterResponseDto.Characters getCharacters(int currenPage, int size);
}
