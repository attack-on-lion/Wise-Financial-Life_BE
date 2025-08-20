package com.wisespendinglife.wise_spending_life.domain.composite.service;

import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.composite.entity.Composite;

public interface CompositeService {
    CompositeResponseDto.Composites getComposites(int currenPage, int size);
    CompositeResponseDto.CreateResponse addComposite(CompositeRequestDto.CreatComposite requestDto);
    Composite getEntity(Long compositeId);
}
