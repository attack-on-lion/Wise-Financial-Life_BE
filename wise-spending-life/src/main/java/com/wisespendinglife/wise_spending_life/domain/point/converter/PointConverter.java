package com.wisespendinglife.wise_spending_life.domain.point.converter;

import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PointConverter {

    /** Entity -> DTO.Item */
    public PointResponseDto.Item toItemDto(Point e) {
        Objects.requireNonNull(e, "PointLedgerEntity must not be null");
        return PointResponseDto.Item.builder()
                .id(e.getId())
                .userId(e.getUser().getId())
                .sourceKind(e.getSourceKind())      // enum 그대로
                .delta(e.getDelta())
                .balance(e.getBalance())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    /** List<Entity> -> List<DTO.Item> */
    public List<PointResponseDto.Item> toItemDtoList(List<Point> entities) {
        return entities.stream().map(this::toItemDto).collect(Collectors.toList());
    }



}
