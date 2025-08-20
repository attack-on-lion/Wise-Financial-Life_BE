package com.wisespendinglife.wise_spending_life.domain.character.controller;

import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService service;

    /**
     * 상점 판매 캐릭터 리스트 반환
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/store/characters")
    public ResponseEntity<CharacterResponseDto.Characters> storeCharacters(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
            ) {

        log.info(">>> [CTRL] /api/store/characters?page={}, size={} GET", page, size);

        return ResponseEntity.ok(service.getCharacters(page, size));
    }

    /**
     * 상점에 판매 캐릭터 추가
     * @param createDto
     * @return
     */
    @PostMapping("/store/characters")
    public ResponseEntity<CharacterResponseDto.CharacterCreateDto> addCharacter(
            @Validated @RequestBody CharacterRequestDto.CreateCharacterDto createDto
            ){

        log.info(">>> [CTRL] /api/store/characters POST -> {}", createDto);

        return ResponseEntity.ok(service.saveCharacter(createDto));
    }

}
