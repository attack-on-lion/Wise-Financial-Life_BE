package com.wisespendinglife.wise_spending_life.domain.notification.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.service.CategoryService;
import com.wisespendinglife.wise_spending_life.domain.notification.converter.NoticeConverter;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.notification.entity.Notification;
import com.wisespendinglife.wise_spending_life.domain.notification.repository.NoticeRepository;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserReadService;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class NoticeCommandServiceImpl implements NoticeCommandService{

    private final NoticeRepository noticeRepository;
    private final NoticeConverter noticeConverter;
    private final UserReadService userReadService;
    private final CategoryService categoryService;

    @Override
    public NoticeResponseDto.NoticCreateResponseDto createNotice(NoticeRequestDto.CreateNoticDto requestDto) {

        User user = userReadService.getEntity(requestDto.getUserId());
        Category category = categoryService.getEntity(requestDto.getCategoryName().name());

        Notification save = noticeRepository.save(noticeConverter.toEntity(requestDto, user, category));

        log.info(">>> [SERVICE] createNotice -> {}", requestDto.toString());

        return noticeConverter.toCreateResponseDto(save);
    }

    @Override
    public NoticeResponseDto.NoticeDeleteResponseDto deleteNotice(Long noticeId) {

        Notification entity = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOTIFICATION_NOT_FOUND));

        boolean result = entity.updateIsDeleted(true);

        log.info(">>> [SERVICE] deleteNotice -> noticeId: {}", noticeId);

        return noticeConverter.toDeleteResponseDto(result);
    }
}
