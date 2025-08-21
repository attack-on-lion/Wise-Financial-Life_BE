package com.wisespendinglife.wise_spending_life.domain.notification.controller;

import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NotificationType;
import com.wisespendinglife.wise_spending_life.domain.notification.service.NoticeCommandService;
import com.wisespendinglife.wise_spending_life.domain.notification.service.NoticeQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeCommandService noticeCommandService;
    private final NoticeQueryService noticeQueryService;

    @GetMapping("/users/{user_id}/notifications")
    public ResponseEntity<NoticeResponseDto.Notices> getNotifications(
            @PathVariable("user_id") Long userId,
            @RequestParam(required = false) NotificationType category,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "15") int size
    ) {

        log.info(">>> [CTRL] /api/users/{}/notifications?category={}, page={}, size={}", userId, category, page, size);

        return ResponseEntity.ok(noticeQueryService.getNotices(userId, category, page, size));

    }

    @PostMapping("/api/notifications/{user_id}")
    public ResponseEntity<NoticeResponseDto.NoticCreateResponseDto> addNotification(
            @PathVariable("user_id") Long userId,
            @Validated @RequestBody NoticeRequestDto.CreateNoticDto requestDto
    ) {

        log.info(">>> [CTRL] /api/notifications/{} POST -> {}", userId, requestDto);

        return ResponseEntity.ok(noticeCommandService.createNotice(requestDto, userId));
    }

    @DeleteMapping("/api/notifications/{notice_id}")
    public ResponseEntity<NoticeResponseDto.NoticeDeleteResponseDto> deleteNotification(
            @PathVariable("notice_id") Long noticeId
    ) {

        log.info(">>> [CTRL] /api/notifications/{} DELETE", noticeId);

        return ResponseEntity.ok(noticeCommandService.deleteNotice(noticeId));
    }
}
