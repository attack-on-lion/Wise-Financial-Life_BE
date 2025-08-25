package com.wisespendinglife.wise_spending_life.domain.user.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.composite.entity.Composite;
import com.wisespendinglife.wise_spending_life.domain.composite.service.CompositeService;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userCharService.UserCharServiceImpl;
import com.wisespendinglife.wise_spending_life.domain.user.ownership.service.userItemService.UserItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisespendinglife.wise_spending_life.domain.user.converter.UserConverter;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
//예외처리
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    //유저 정보 조회
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CompositeService compositeService;
    private final UserCharServiceImpl userCharService;
    private final UserItemService userItemService;

    @Override
    public void updateComposite(Long userId, Long CompositeId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Composite composite = compositeService.getEntity(CompositeId);

        /**
         * 유저의 소유 여부 확인.
         * 각 메소드 안에서 예외 처리
         */
        userCharService.isOwned(user.getId(), composite.getId());
        userItemService.isOwned(user.getId(), composite.getId());

        /**
         * 소유권 문제 없으면 업데이트 진행
         */
        user.updateComposite(composite);

        log.info(">>> [SERVICE] Updated composite -> userId:{}, CompositeId:{} ", user.getId(), CompositeId);

    }

    @Override
    public UserResponseDTO getUserInfo(Long userId){
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        log.info(">>> [SERVICE] Get user -> {}", user.toString());

        return UserConverter.toResponseDTO(user);
    }

    //유저 정보 수정
    @Override
    public void updateUserInfo(Long userId, UserRequestDTO dto){
        User user = userRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (dto.getName() != null) user.updateName(dto.getName());
        if (dto.getProfileImgUrl() != null) user.updateProfileImgUrl(dto.getProfileImgUrl());
        if (dto.getGender() != null) user.updateGender(dto.getGender());
        if (dto.getLocation() != null) user.updateLocation(dto.getLocation());
        if (dto.getEmail() != null) user.updateEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.updatePhoneNumber(dto.getPhoneNumber());
        if (dto.getAge() != null) user.updateAge(dto.getAge());
        if (dto.getBaseAmount() != null) user.updateBaseAmount(dto.getBaseAmount());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            user.updateCategory(category);
        }

        log.info(">>> [SERVICE] Updated user -> {}", user.toString());
    }

    // 유저 정보 저장
    @Override
    public Long createUser(UserRequestDTO dto){

        Category category = null;
        if(dto.getCategoryId() != null){
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
        }

        User user = UserConverter.toEntity(dto, category);
        userRepository.save(user);
        log.info(">>> [SERVICE] Created user -> {}", user.toString());
        return user.getId();
    }

}
