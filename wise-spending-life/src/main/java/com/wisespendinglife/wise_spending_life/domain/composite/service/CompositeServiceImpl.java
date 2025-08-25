package com.wisespendinglife.wise_spending_life.domain.composite.service;

import com.wisespendinglife.wise_spending_life.domain.character.entity.Character;
import com.wisespendinglife.wise_spending_life.domain.character.service.CharacterService;
import com.wisespendinglife.wise_spending_life.domain.composite.assembler.CompositeAssembler;
import com.wisespendinglife.wise_spending_life.domain.composite.converter.CompositeConverter;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeRequestDto;
import com.wisespendinglife.wise_spending_life.domain.composite.dto.CompositeResponseDto;
import com.wisespendinglife.wise_spending_life.domain.composite.entity.Composite;
import com.wisespendinglife.wise_spending_life.domain.composite.repository.CompositeRepository;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import com.wisespendinglife.wise_spending_life.domain.item.service.ItemService;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CompositeServiceImpl implements CompositeService {

    private final CompositeRepository compositeRepository;
    private final CompositeConverter compositeConverter;
    private final CompositeAssembler compositeAssembler;
    private final CharacterService characterService;
    private final ItemService itemService;

    @Override
    public CompositeResponseDto.Composites getComposites(int currenPage, int size) {

        Pageable pageable = PageRequest.of(currenPage, size, Sort.by("id").descending());

        Page<Composite> all = compositeRepository.findAll(pageable);

        log.info(">>> [SERVICE] getAllComposites");

        return compositeAssembler.assemble(all);
    }

    @Override
    public CompositeResponseDto.CreateResponse addComposite(CompositeRequestDto.CreatComposite requestDto) {

        Character character = characterService.getEntity(requestDto.getCharacterName());
        Item item = itemService.getEntity(requestDto.getItemName());

        Composite entity = compositeConverter.toEntity(requestDto, item, character);
        Composite save = compositeRepository.save(entity);

        return compositeConverter.toCreateResponse(save);
    }

    @Override
    public Composite getEntity(Long compositeId) {

        Composite composite = compositeRepository.findById(compositeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMPOSITE_NOT_FOUND));

        return composite;
    }
}
