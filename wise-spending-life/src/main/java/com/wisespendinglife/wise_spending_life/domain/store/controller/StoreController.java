package com.wisespendinglife.wise_spending_life.domain.store.controller;

import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonListResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.service.GifticonService;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreListResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.store.dto.StoreResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stores")
public class StoreController {
    private final StoreService storeService;
    private final GifticonService gifticonService;

        //제휴 상점 리스트 (기프티콘 리스트 그대로 가져온것)
        @GetMapping
        public ResponseEntity<GifticonListResponseDTO> getAllGifticons(
                @RequestParam(required = false) LocalDateTime lastCreatedAt,
                @RequestParam(required = false) Long lastId,
                @RequestParam(defaultValue = "10") int size) {

            return ResponseEntity.ok(gifticonService.getAllGifticon(lastCreatedAt, lastId, size));
        }


        //제휴 상점 추가 (상점 새로 등록. 이미지, 브랜드명 필요)
        @PostMapping
        public ResponseEntity<Map<String, String>> createStore(@Valid @RequestBody StoreRequestDTO requestDto) {
            Long newId = storeService.createStore(requestDto);

            URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newId)
                    .toUri();

            return ResponseEntity
                    .created(location)
                    .body(Map.of(
                            "msg", "제휴 상점이 성공적으로 등록되었습니다."));
        }

        @DeleteMapping("/{store_id}")
        public ResponseEntity<Map<String, String>> deleteStore(@PathVariable("store_id") Long storeId) {
            storeService.deleteStore(storeId);
            return ResponseEntity
                    .ok(Map.of("msg", "제휴 상점이 삭제 되었습니다."));
        }

        //제휴 상점 브랜드 리스트 (브랜드 목록 전체 조회)
        @GetMapping("/brand")
        public ResponseEntity<StoreListResponseDTO> getAllBrands(
                @RequestParam(required = false) String lastStoreName,
                @RequestParam(required = false) Long lastId,
                @RequestParam(defaultValue = "10") int size // 10개 단위 고정 운용이면 default 10
        ) {
            var body = storeService.getAllStores(lastStoreName, lastId, size);
            return ResponseEntity.ok(body);
        }

    }

