package com.wisespendinglife.wise_spending_life.domain.user;


import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import com.wisespendinglife.wise_spending_life.domain.user.repository.UserRepository;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class UserDomainTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("유저 저장 및 조회 테스트")
    public void createUserTest() {
        // given
        UserRequestDTO dto = seedUser();

        // when
        Long userId = userService.createUser(dto);
        UserResponseDTO userInfo = userService.getUserInfo(userId);

        // then
        Assertions.assertThat(userInfo.getName()).isEqualTo(dto.getName());
    }

    @Test
    @DisplayName("유저 수정 테스트")
    public void patchUser(){
        // given
        UserRequestDTO dto = seedUser();
        Long userId = userService.createUser(dto);

        // when
        UserRequestDTO newDto = UserRequestDTO.builder()
                .name("테스트2")
                .build();
        userService.updateUserInfo(userId, newDto);

        // then

        UserResponseDTO userInfo = userService.getUserInfo(userId);
        Assertions.assertThat(userInfo.getName()).isEqualTo(newDto.getName());
    }

    private UserRequestDTO seedUser() {
        return UserRequestDTO.builder()
                    .name("테스트")
                    .email("korea@kw.ac.kr")
                    .age(20L)
                    .gender("남자")
                    .location("서울시 강북구")
                    .baseAmount(100000L)
                    .phoneNumber("010-1234-5678")
                    .profileImgUrl("https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png")
                    .build();
    }

}
