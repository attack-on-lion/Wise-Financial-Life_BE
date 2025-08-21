package com.wisespendinglife.wise_spending_life.domain.notification.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.entity.CategoryType;
import com.wisespendinglife.wise_spending_life.domain.category.service.CategoryService;
import com.wisespendinglife.wise_spending_life.domain.notification.assembler.NoticeAssembler;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NotificationType;
import com.wisespendinglife.wise_spending_life.domain.notification.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class NoticeQueryServiceImpl implements NoticeQueryService{

    private final NoticeRepository noticeRepository;
    private final CategoryService categoryService;
    private final NoticeAssembler noticeAssembler;

    @Override
    public NoticeResponseDto.Notices getNotices(Long userId, NotificationType type, int currenPage, int size) {

        Category category = null;
        if(type != null){
            category = categoryService.getEntity(type.name());
        }

        Pageable pageable = PageRequest.of(currenPage, size);


        // type에 따라서 조회
        if(type == null)
        {
            /** type이 없을 경우 모든 알림을 조회 */
            return noticeAssembler.assemble(
                    noticeRepository.findByUser_IdAndIsDeletedFalseOrderByCreatedAtDesc(userId, pageable));

        } else {

            /** type이 있을 경우 해당 카테고리의 알림을 조회 */
            return noticeAssembler.assemble(
                    noticeRepository.findByUser_IdAndCategory_IdAndIsDeletedFalseOrderByCreatedAtDesc(userId, category.getId(), pageable));

        }

    }
}
