package com.wisespendinglife.wise_spending_life.domain.notification.converter;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.notification.entity.Notification;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoticeConverter {

    public NoticeResponseDto.NoticCreateResponseDto toCreateResponseDto(Notification entity) {
        return NoticeResponseDto.NoticCreateResponseDto.builder()
                .id(entity.getId())
                .build();
    }

    public Notification toEntity(NoticeRequestDto.CreateNoticDto requestDto, User user, Category category) {
         return Notification.builder()
                 .user(user)
                 .content(requestDto.getContent())
                 .deeplink(requestDto.getDeeplink())
                 .category(category)
                 .isDeleted(false)
                 .build();
     }

    NoticeResponseDto.Notice toNoticeDto(Notification entity) {
        return NoticeResponseDto.Notice.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .content(entity.getContent())
                .deeplink(entity.getDeeplink())
                .categoryName(entity.getCategory().getName())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    List<NoticeResponseDto.Notice> toNoticeDtos(List<Notification> entities) {
        return entities.stream()
                .map(this::toNoticeDto)
                .toList();
    }

    public NoticeResponseDto.Items toNoticeListDto(List<Notification> entities) {
        return NoticeResponseDto.Items.builder()
                .notices(toNoticeDtos(entities))
                .build();
    }

    public NoticeResponseDto.NoticeDeleteResponseDto toDeleteResponseDto(boolean isDeleted) {
        return NoticeResponseDto.NoticeDeleteResponseDto.builder()
                .isSuccess(isDeleted)
                .build();
    }

}
