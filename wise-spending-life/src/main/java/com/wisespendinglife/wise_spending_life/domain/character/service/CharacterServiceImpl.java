package com.wisespendinglife.wise_spending_life.domain.character.service;

import com.wisespendinglife.wise_spending_life.domain.character.assembler.CharacterAssembler;
import com.wisespendinglife.wise_spending_life.domain.character.converter.CharacterConverter;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.character.repository.CharacterRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CharacterServiceImpl implements CharacterService{

    private final CharacterRepository characterRepository;
    private final CharacterAssembler assembler;
    private final CharacterConverter converter;

    @Override
    public Character getEntity(Long characterId) {
        return characterRepository.findById(characterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHARACTER_NOT_FOUND));
    }

    /**
     * 상점에서 판매중인 캐릭터 리스트
     * @param currenPage - 현재 페이지 (기본값: 0)
     * @param size - 현재 페이지에서 반환 할 개수
     * @return - 캐릭터 레코드 객체가 담겨있는 DTO
     */
    @Override
    public CharacterResponseDto.Characters getCharacters(int currenPage, int size) {

        Pageable pageable = PageRequest.of(currenPage, size, Sort.by("id").descending());

        Page<Character> all = characterRepository.findAll(pageable);

        log.info(">>> [SERVICE] getCharacters");

        return assembler.assemble(all);
    }

    /**
     * 판매 캐릭터 추가
     * @param requestDto - 캐릭터 추가에 필요한 Dto
     * @return - 캐릭터 id
     */
    @Override
    public CharacterResponseDto.CharacterCreateDto saveCharacter(CharacterRequestDto.CreateCharacterDto requestDto) {

        Character entity = converter.toEntity(requestDto);
        entity = characterRepository.save(entity);

        log.info(">>> [SERVICE] saveCharacter -> {}", entity.toString());

        return converter.toCreateResponseDto(entity);
    }

    @Override
    public Character getEntity(String name) {

        Character entity = characterRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.CHARACTER_NOT_FOUND));

        log.info(">>> [SERVICE] getEntity -> {}", entity.toString());

        return entity;
    }
}
