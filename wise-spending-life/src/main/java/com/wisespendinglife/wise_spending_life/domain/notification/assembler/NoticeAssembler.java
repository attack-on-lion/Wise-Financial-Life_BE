package com.wisespendinglife.wise_spending_life.domain.notification.assembler;

import com.wisespendinglife.wise_spending_life.domain.notification.converter.NoticConverter;
import com.wisespendinglife.wise_spending_life.domain.notification.dto.NoticeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoticeAssembler {

    private final NoticConverter noticConverter;

    public NoticeResponseDto.Notices assemble(Page<Notification> page) {


        NoticeResponseDto.Summary summary = NoticeResponseDto.Summary.builder()
                .count(page.getNumberOfElements())
                .build();

        NoticeResponseDto.PageInfo pageInfo = NoticeResponseDto.PageInfo.builder()
                .page(page.getNumber())
                .size(page.getTotalPages())
                .hasNext(page.hasNext())
                .build();

        return NoticeResponseDto.Notices.builder()
                .summary(summary)
                .pageInfo(pageInfo)
                .items(noticConverter.toNoticeListDto(page.getContent()))
                .build();

    }


}
