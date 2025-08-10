package com.wisespendinglife.wise_spending_life.domain.solution.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimpleSolutionResponseDTO {
    private String message;  // 한 줄 메시지
    private List<String> solution;  // 불릿 솔루션
}