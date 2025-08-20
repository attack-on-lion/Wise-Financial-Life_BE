package com.wisespendinglife.wise_spending_life.domain.item.assembler;

import com.wisespendinglife.wise_spending_life.domain.item.converter.ItemConverter;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemAssembler {

    private final ItemConverter converter;

    public ItemResponseDto.Items assemble(Page<Item> page){

        /** summary */
        ItemResponseDto.Summary summary = ItemResponseDto.Summary.builder()
                .count(page.getNumberOfElements())
                .build();

        /** pageInfo */
        ItemResponseDto.PageInfo pageInfo = ItemResponseDto.PageInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .build();

        /** items */
        List<ItemResponseDto.Item> items = converter.toItemDtos(page.getContent());

        return converter.assembleItemsDto(summary, pageInfo, items);

    }

}
