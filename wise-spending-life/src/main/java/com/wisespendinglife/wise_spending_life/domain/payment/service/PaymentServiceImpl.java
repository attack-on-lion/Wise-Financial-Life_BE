package com.wisespendinglife.wise_spending_life.domain.payment.service;

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;
import com.wisespendinglife.wise_spending_life.domain.category.repository.CategoryRepository;
import com.wisespendinglife.wise_spending_life.domain.payment.assembler.PaymentResponseAssembler;
import com.wisespendinglife.wise_spending_life.domain.payment.converter.PaymentConverter;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentRequestDto;
import com.wisespendinglife.wise_spending_life.domain.payment.dto.PaymentResponseDto;
import com.wisespendinglife.wise_spending_life.domain.payment.entity.Payment;
import com.wisespendinglife.wise_spending_life.domain.payment.repository.PaymentRepository;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CategoryRepository categoryRepository;
    private final PaymentConverter converter;
    private final PaymentResponseAssembler responseAssembler;

    public PaymentResponseDto.Payments getMonthly(LocalDate from,
                                                  LocalDate to,
                                                  int currentPage,
                                                  int size,
                                                  Optional<String> categoryOpt) {

        if(from.isAfter(to)) throw new BusinessException(ErrorCode.INVALID_DATE_REQUEST);

        LocalDateTime start = from.atStartOfDay();          // 2025-07-01 00:00
        LocalDateTime end   = to.plusDays(1).atStartOfDay();

        // Pageable 설정
        Pageable pageable = PageRequest.of(currentPage, size, Sort.by(Sort.Order.desc("transactionAt"),
                Sort.Order.desc("id")).descending());

        // Page 조회
        Page<Payment> page = categoryOpt
                .map(cat -> {
                    // ❶ 존재 여부 검증
                    if (!categoryRepository.existsByNameIgnoreCase(cat))
                        throw new BusinessException(ErrorCode.CATEGORY_NOT_FOUND);

                    // ❷ 정상이라면 조회
                    return paymentRepository
                            .findByCategory_NameIgnoreCaseAndTransactionAtBetween(
                                    cat, start, end, pageable);
                })
                .orElseGet(() -> paymentRepository
                        .findByTransactionAtBetween(start, end, pageable));

        // Response 생성
        return responseAssembler.assemble(from, to, page);
    }

    @Transactional
    public PaymentResponseDto.PaymentCreateResponseDto create(PaymentRequestDto.CreateDto dto) {

        // 1) 카테고리 찾기 (대소문자 무시)
        Category category = categoryRepository.findByNameIgnoreCase(dto.getCategory())
                .orElseGet(() -> {
                    Category newCategory = Category.builder()
                            .name(dto.getCategory())
                            .build();
                    return categoryRepository.save(newCategory);
                });

        // 2) 엔티티 변환 & 저장
        Payment payment = converter.toEntity(dto, category);
        Payment saved = paymentRepository.save(payment);

        // 3) 응답 DTO 변환
        return converter.toCreateResponseDto(saved.getId());
    }

}
