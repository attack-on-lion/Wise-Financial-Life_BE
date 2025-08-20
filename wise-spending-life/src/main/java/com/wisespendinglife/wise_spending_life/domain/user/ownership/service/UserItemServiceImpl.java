package com.wisespendinglife.wise_spending_life.domain.user.ownership.service;

import com.wisespendinglife.wise_spending_life.domain.item.entity.Item;
import com.wisespendinglife.wise_spending_life.domain.item.service.ItemService;
import com.wisespendinglife.wise_spending_life.domain.point.converter.PointConverter;
import com.wisespendinglife.wise_spending_life.domain.point.dto.PointRequestDto;
import com.wisespendinglife.wise_spending_life.domain.point.entity.SourceKind;
import com.wisespendinglife.wise_spending_life.domain.point.service.PointService;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.converter.UserItemConverter;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemRequestDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.dto.userItem.UserItemResponseDto;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.entity.UserItem;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.repository.UserItemRepository;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserReadServiceImpl;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserService;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Transactional
@Slf4j
public class UserItemServiceImpl implements UserItemService {

    private final UserItemRepository userItemRepository;
    private final UserItemConverter userItemConverter;
    private final ItemService itemService;
    private final PointConverter pointConverter;
    private final PointService pointService;
    private final UserReadServiceImpl userReadService;

    @Override
    @Transactional(readOnly = true)
    public UserItemResponseDto.OwnedItemListDto getOwnedItems(Long userId) {

        List<UserItem> ownedUserItemList = userItemRepository.findByUser_IdOrderByIdDesc(userId);

        return userItemConverter.toOwnedListDto(ownedUserItemList);
    }

    @Override
    public UserItemResponseDto.PurchaseItemDto addUserItem(
            UserItemRequestDto.PurchaseItemDto requestDto,
            Long userId,
            Long itemId) {

        User user = userReadService.getEntity(userId);
        Item item = itemService.getEntity(itemId);

        UserItem userItem = userItemConverter.toEntity(user, item);

        PointRequestDto.SpendPointRequestDto spendPointRequestDto =
                pointConverter.toSpendPointRequestDto(-requestDto.getPrice(), SourceKind.purchase);

        pointService.handlePointChange(userId, spendPointRequestDto);

        UserItem save = userItemRepository.save(userItem);


        return userItemConverter.toPurchaseItemResponseDto(save);
    }

    @Override
    public Boolean isOwned(Long userId, Long itemId) {
        boolean result = userItemRepository.existsByUser_IdAndItem_Id(userId, itemId);

        if (!result) {
            throw new BusinessException(ErrorCode.INVALID_ITEM_REQUEST);
        }

        return result;
    }
}
