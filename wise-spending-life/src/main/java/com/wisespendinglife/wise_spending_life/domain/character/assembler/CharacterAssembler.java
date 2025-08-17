package com.wisespendinglife.wise_spending_life.domain.character.assembler;

import com.wisespendinglife.wise_spending_life.domain.character.converter.CharacterConverter;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterRequestDto;
import com.wisespendinglife.wise_spending_life.domain.character.dto.CharacterResponseDto;
import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterAssembler {

    private final CharacterConverter converter;

    public CharacterResponseDto.Characters assemble(LocalDate from,
                                                    LocalDate to,
                                                    Page<Character> page) {

        // 1) summary 계산
        var summary = CharacterResponseDto.Summary.builder()
                .count(page.getNumberOfElements())
                .build();

        // 2) pageInfo 조립
        var pageInfo = CharacterResponseDto.PageInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .hasNext(page.hasNext())
                .build();

        // 3) item 리스트 변환
        List<CharacterResponseDto.Item> items =
                converter.toItemDtos(page.getContent());

        // 4) 최종 DTO 반환
        return CharacterResponseDto.Characters.builder()
                .summary(summary)
                .pageInfo(pageInfo)
                .items(items)
                .build();
    }
}
