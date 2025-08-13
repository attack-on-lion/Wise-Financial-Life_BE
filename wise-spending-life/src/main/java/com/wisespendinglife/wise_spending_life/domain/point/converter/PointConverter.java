package com.wisespendinglife.wise_spending_life.domain.point.converter;

import com.wisespendinglife.wise_spending_life.domain.point.dto.PointDeltaRequest;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointRequestDto;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointResponseDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.Point;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
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

    public PointResponseDto.PointBalanceResponseDto toPointBalanceDto(Point entity) {
        return PointResponseDto.PointBalanceResponseDto.builder()
                .balance(entity.getBalance())
                .build();
    }

    /**
     * EarnPointRequestDto -> Entity 컨버터
     *
     * @param dto EarnPointRequestDto
     * @param user 유저 엔티티
     * @return Point 엔티티
     */
    public Point toEntity(PointDeltaRequest dto, UserEntity user) {
        return Point.builder()
                .user(user)
                .sourceKind(dto.getSourceKind())
                .delta(dto.getDelta())
                .balance(user.getPoint() + dto.getDelta())
                .build();
    }





}
