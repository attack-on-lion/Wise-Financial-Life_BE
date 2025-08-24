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

        //브랜드별 기프티콘 조회
        @GetMapping("/{storeId}/gifticons")
        public ResponseEntity<GifticonListResponseDTO> getGifticonsByStore(
                @PathVariable("storeId") Long storeId,
                @RequestParam(required = false)
                @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME)
                LocalDateTime lastCreatedAt,
                @RequestParam(required = false) Long lastId,
                @RequestParam(defaultValue = "10") int size
        ) {
            GifticonListResponseDTO result =
                    gifticonService.getGifticonsByStore(storeId, lastCreatedAt, lastId, size);
            return ResponseEntity.ok(result);
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

    /**
     * 카테고리로 필터된 브랜드 조회 (커서 기반)
     * 예: /api/stores/brand/by-category?categoryName=CAFE&size=10
     * 첫 호출은 lastStoreName/lastId 없이 호출 → 내부에서 ("", 0L)로 초기화되어 첫 페이지 반환
     */
    @GetMapping("/brand/by-category")
    public ResponseEntity<StoreListResponseDTO> getBrandsByCategory(
            @RequestParam String categoryName,
            @RequestParam(required = false) String lastStoreName,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        var body = storeService.getStoresByCategory(categoryName, lastStoreName, lastId, size);
        return ResponseEntity.ok(body);
    }


}

