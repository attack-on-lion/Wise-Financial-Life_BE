package com.wisespendinglife.wise_spending_life.domain.composite.converter;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.composite.entity.Composite;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeConverter {

    public CompositeResponseDto.CreateResponse toCreateResponse(Composite composite) {
        return CompositeResponseDto.CreateResponse.builder()
                .id(composite.getId())
                .build();
    }

    public Composite toEntity(CompositeRequestDto.CreatComposite requestDto, Item item, Character character) {
        return Composite.builder()
                .item(item)
                .character(character)
                .imageUrl(requestDto.getImageUrl())
                .build();
    }

    public CompositeResponseDto.CompositeResponse toDto(Composite entity) {
        return CompositeResponseDto.CompositeResponse.builder()
                .id(entity.getId())
                .characterId(entity.getCharacter().getId())
                .itemId(entity.getItem().getId())
                .imageUrl(entity.getImageUrl())
                .build();
    }

    public List<CompositeResponseDto.CompositeResponse> toDto(List<Composite> entities) {
        return entities.stream()
                .map(this::toDto)
                .toList();
    }

    public CompositeResponseDto.Composites toListDto(
            List<Composite> entities,
            CompositeResponseDto.Summary summary,
            CompositeResponseDto.PageInfo pageInfo
    ) {
        return CompositeResponseDto.Composites.builder()
                .summary(summary)
                .pageInfo(pageInfo)
                .items(toDto(entities))
                .build();
    }
}
