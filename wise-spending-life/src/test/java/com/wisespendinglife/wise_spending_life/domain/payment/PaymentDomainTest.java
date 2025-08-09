package com.wisespendinglife.wise_spending_life.domain.payment;

import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentDirection;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentMethod;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.service.PaymentServiceImpl;
import com.wisespendinglife.wise_spending_life.domain.user.dto.UserRequestDTO;
import com.wisespendinglife.wise_spending_life.domain.user.service.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto.*;

@SpringBootTest
@Transactional
@ActiveProfiles("local")
public class PaymentDomainTest {

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private UserServiceImpl userService;

    @Test
    @DisplayName("결제 정보 저장 및 조회 테스트")
    public void createPaymentTest() {
        //given
        PaymentRequestDto.CreateDto createDto = seedPayment();
        Long userId = userService.createUser(seedUser());

        // when
        paymentService.create(createDto, userId);

        // then
        Payments monthly = paymentService.getMonthly(LocalDate.now().minusMonths(1), LocalDate.now(), 0, 15, userId, Optional.empty());
        Assertions.assertThat(monthly.getItems().get(0).getStoreName()).isEqualTo(createDto.getStoreName());
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

    private PaymentRequestDto.CreateDto seedPayment() {
        return PaymentRequestDto.CreateDto.builder()
                .transactionAt(LocalDateTime.now())
                .storeName("테스트")
                .amount(10000L)
                .direction(PaymentDirection.OUTFLOW)
                .method(PaymentMethod.CARD)
                .category("식비")
                .build();
    }

    private void createSeedPayment() {
        UserRequestDTO dto = seedUser();
        Long userId = userService.createUser(dto);

        // 식비 10건
        for(int i = 0; i < 11; i++){
            PaymentRequestDto.CreateDto createDto = PaymentRequestDto.CreateDto.builder()
                    .transactionAt(LocalDateTime.now())
                    .storeName("테스트")
                    .amount(10000L)
                    .direction(PaymentDirection.OUTFLOW)
                    .method(PaymentMethod.CARD)
                    .category("식비")
                    .build();
            paymentService.create(createDto, userId);
        }

        // 교육 5건
        for(int i = 0; i < 6; i++){
            PaymentRequestDto.CreateDto createDto = PaymentRequestDto.CreateDto.builder()
                    .transactionAt(LocalDateTime.now())
                    .storeName("테스트")
                    .amount(10000L)
                    .direction(PaymentDirection.OUTFLOW)
                    .method(PaymentMethod.CARD)
                    .category("교육")
                    .build();
            paymentService.create(createDto, userId);
        }

        // 유흥 13건
        for(int i = 0; i < 14; i++){
            PaymentRequestDto.CreateDto createDto = PaymentRequestDto.CreateDto.builder()
                    .transactionAt(LocalDateTime.now())
                    .storeName("테스트")
                    .amount(10000L)
                    .direction(PaymentDirection.OUTFLOW)
                    .method(PaymentMethod.CARD)
                    .category("유흥")
                    .build();
            paymentService.create(createDto, userId);
        }


    }

}
