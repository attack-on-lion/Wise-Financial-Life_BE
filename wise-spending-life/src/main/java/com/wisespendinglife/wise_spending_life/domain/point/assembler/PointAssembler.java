package com.wisespendinglife.wise_spending_life.domain.point.assembler;

import com.wisespendinglife.wise_spending_life.domain.point.converter.PointConverter;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PointAssembler {

    private final PointConverter converter;


    public PointResponseDto.PointListResponseDto assemble(
            long totalCount,
            int page,
            int size,
            boolean hasNext,
            List<Point> rows
    ) {
        List<PointResponseDto.Item> items = converter.toItemDtoList(rows);

        return PointResponseDto.PointListResponseDto.builder()
                .summary(PointResponseDto.Summary.builder()
                        .count(totalCount)
                        .build())
                .pageInfo(PointResponseDto.PageInfo.builder()
                        .page(page)
                        .size(size)
                        .hasNext(hasNext)
                        .build())
                .items(items)
                .build();
    }
}
