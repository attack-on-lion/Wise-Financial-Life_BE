package com.wisespendinglife.wise_spending_life.domain.notification.service;

import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;

public interface NoticeCommandService {
    NoticeResponseDto.NoticCreateResponseDto createNotice(NoticeRequestDto.CreateNoticDto requestDto);
    NoticeResponseDto.NoticeDeleteResponseDto deleteNotice(Long noticeId);
}
