package com.wisespendinglife.wise_spending_life.domain.composite.controller;

import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.composite.service.CompositeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CompositeController {
    private final CompositeService compositeService;

    @GetMapping("/composites")
    public ResponseEntity<CompositeResponseDto.Composites> getAllComposites(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
    ) {

        log.info(">>> [CTRL] /api/composites?page={}, size={} GET", page, size);

        return ResponseEntity.ok(compositeService.getComposites(page, size));
    }

    /**
     * 캐릭터-아이템 조합을 생성하는 API
     * @param requestDto
     * @return
     */
    @PostMapping("/composites")
    public ResponseEntity<CompositeResponseDto.CreateResponse> createComposite(
            @Validated @RequestBody CompositeRequestDto.CreatComposite requestDto
    ){

        log.info(">>> [CTRL] /api/composite -> {} POST", requestDto.toString());

        return ResponseEntity.ok(compositeService.addComposite(requestDto));
    }

}
