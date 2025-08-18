package com.wisespendinglife.wise_spending_life.domain.item.controller;

import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.item.service.ItemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemServiceImpl itemService;


    /**
     * 상점에 표시될 모든 아이템을 반환
     * @param page - 현재 페이지
     * @param size - 반환 할 개수
     * @return
     */
    @GetMapping("/api/store/items")
    ResponseEntity<ItemResponseDto.Items> getAllItems(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
            ) {

        log.info(">>> [CTRL] /api/store/items?page={}, size={} GET", page, size);

        return ResponseEntity.ok(itemService.getItems(page, size));
    }
}
