package com.wisespendinglife.wise_spending_life.domain.item.service;

import com.wisespendinglife.wise_spending_life.domain.item.assembler.ItemAssembler;
import com.wisespendinglife.wise_spending_life.domain.item.converter.ItemConverter;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemRequestDto;
import com.wisespendinglife.wise_spending_life.domain.item.dto.ItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import com.wisespendinglife.wise_spending_life.domain.item.repository.ItemRepository;
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
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemAssembler itemAssembler;
    private final ItemConverter itemConverter;

    /**
     * 아이템 목록 리스트 반환
     * @param currenPage - 현재 페이지
     * @param size - 반환 할 개수
     * @return
     */
    @Override
    public ItemResponseDto.Items getItems(int currenPage, int size) {

        Pageable pageable = PageRequest.of(currenPage, size, Sort.by("id").descending());

        Page<Item> all = itemRepository.findAll(pageable);

        log.info(">>> [SERVICE] getAllItems");

        return itemAssembler.assemble(all);
    }

    /**
     * 아이템 저장 메소드
     * @param requestDto - 저장 요청용 Dto
     * @return
     */
    @Override
    public ItemResponseDto.ItemCreateDto saveItem(ItemRequestDto.CreateItemDto requestDto) {

        Item entity = itemConverter.toEntity(requestDto);

        Item save = itemRepository.save(entity);

        log.info(">>> [SERVICE] saveItem -> {}", entity.toString());

        return itemConverter.toCreateResponseDto(save);
    }

    /**
     * 아이템 엔티티 반환용 메소드
     * @param itemId - 아이템 아이디
     * @return
     */
    @Override
    public Item getEntity(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
        return item;
    }

    @Override
    public Item getEntity(String name) {

        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));

        return item;
    }
}
