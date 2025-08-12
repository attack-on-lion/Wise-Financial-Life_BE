package com.wisespendinglife.wise_spending_life.domain.gifticon.controller;

import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.dto.GifticonRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.gifticon.service.GifticonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gifticons")
public class GifticonController {
    private final GifticonService gifticonService;


    //기프티콘 전체 조회
    @GetMapping
    public ResponseEntity<List<GifticonResponseDTO>> getAllGifticon(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastCreatedAt,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size
    ) {
        Timestamp ts = (lastCreatedAt == null) ? null : Timestamp.valueOf(lastCreatedAt);
        List<GifticonResponseDTO> list = gifticonService.getAllGifticon(ts, lastId, size);
        return ResponseEntity.ok(list);
    }


    //기프티콘 추가
    @PostMapping
    public ResponseEntity<String> createGifticon(@Valid @RequestBody GifticonRequestDTO request) {
        Long newId = gifticonService.createGifticon(request.getStoreId(), request);

        URI location = org.springframework.web.servlet.support.ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(newId)
                .toUri();

        return ResponseEntity
                .status(org.springframework.http.HttpStatus.CREATED)
                .location(location)
                .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
                .body("상품 등록이 완료되었습니다.");
    }


    //기프티콘 삭제
    // 기프티콘 삭제 (소프트 삭제)
    @DeleteMapping("/{gifticon_id}")
    public ResponseEntity<?> deleteGifticon(@PathVariable("gifticon_id") Long gifticonId) {
        gifticonService.deleteGifticon(gifticonId);

        record Msg(String msg) {}
        return ResponseEntity.ok(new Msg("기프티콘이 삭제 되었습니다."));
    }

}
