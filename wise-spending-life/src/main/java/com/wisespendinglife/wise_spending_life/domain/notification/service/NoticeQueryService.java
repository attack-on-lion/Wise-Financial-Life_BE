package com.wisespendinglife.wise_spending_life.domain.notification.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NotificationType;

public interface NoticeQueryService {

    NoticeResponseDto.Notices getNotices(Long userId, NotificationType type, int currenPage, int size);
}
