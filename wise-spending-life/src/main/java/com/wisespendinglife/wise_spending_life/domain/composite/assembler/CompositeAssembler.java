package com.wisespendinglife.wise_spending_life.domain.composite.assembler;

import com.wisespendinglife.wise_spending_life.domain.composite.converter.CompositeConverter;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.composite.entity.Composite;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompositeAssembler {

    private final CompositeConverter compositeConverter;

    public CompositeResponseDto.Composites assemble(Page<Composite> page) {

        // 1. Summary
        CompositeResponseDto.Summary summary = CompositeResponseDto.Summary.builder()
                .count(page.getNumberOfElements())
                .build();

        // 2. PageInfo
        CompositeResponseDto.PageInfo pageInfo = CompositeResponseDto.PageInfo.builder()
                .size(page.getSize())
                .page(page.getNumber())
                .hasNext(page.hasNext())
                .build();


        return compositeConverter.toListDto(page.getContent(), summary, pageInfo);

    }
}
