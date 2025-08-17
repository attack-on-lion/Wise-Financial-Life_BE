package com.wisespendinglife.wise_spending_life.domain.character.controller;

import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService service;

    @GetMapping("/store/characters")
    public ResponseEntity<CharacterResponseDto.Characters> storeCharacters(
            @RequestParam(required = false, defaultValue = "0") int currentPage,
            @RequestParam(required = false, defaultValue = "15") int size
            ) {

        return ResponseEntity.ok(service.getCharacters(currentPage, size));
    }

}
