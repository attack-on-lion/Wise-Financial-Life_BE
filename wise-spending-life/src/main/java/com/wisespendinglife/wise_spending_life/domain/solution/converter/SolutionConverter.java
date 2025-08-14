package com.wisespendinglife.wise_spending_life.domain.solution.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wisespendinglife.wise_spending_life.domain.solution.dto.SimpleSolutionResponseDTO;
import com.wisespendinglife.wise_spending_life.domain.solution.entity.Solution;
import com.wisespendinglife.wise_spending_life.domain.user.entity.User;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolutionConverter {

    private final ObjectMapper objectMapper;

    public JsonNode toJson(SimpleSolutionResponseDTO dto) {
        return objectMapper.valueToTree(dto);
    }

    public SimpleSolutionResponseDTO toResponseDto(String aiJson) {
        try {
            return objectMapper.readValue(aiJson, SimpleSolutionResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.GPT_RESPONSE_PARSE_ERROR);
        }

    }

    public Solution toEntity(User user, SimpleSolutionResponseDTO dto) {
        return Solution.builder()
                .user(user)
                .solution(toJson(dto))
                .build();
    }
}