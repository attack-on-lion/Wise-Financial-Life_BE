package com.wisespendinglife.wise_spending_life.domain.score.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.wisespendinglife.wise_spending_life.domain.score.dto.MonthlyState;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatGptScoringClient {

    private final OpenAIClient openAI;
    private final ObjectMapper mapper;

    public int askScore(MonthlyState stats) {

        /* ① 파라미터 빌드 (README 패턴) */
        String system = """
        너는 금융 전문가다. 다음 규칙을 지켜서만 답한다.
        - 반드시 JSON 객체 하나만 출력한다.
        - 키 이름은 정확히 "score" 하나만 허용한다.
        - 값은 0 이상 100 이하의 정수.
        - JSON 외 텍스트/설명/코드블록/추가 키 금지.
        """;
        String userJson;
        try {
            userJson = mapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_4_1)
                .addSystemMessage(system)
                .addUserMessage(userJson)
                .maxTokens(40)
                .temperature(0.0)
                .build();

        /* ② 호출 */
        ChatCompletion completion = openAI.chat()
                .completions()
                .create(params);

        /* ③ content 추출 — README 권장 방식 (Optional 처리) */
        String content = completion.choices().stream()               // List<Choice>
                .findFirst()                                         // 첫 choice
                .flatMap(choice -> choice.message().content())       // Optional<String>
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.GPT_EMPTY_RESPONSE));

        /* ④ Jackson 파싱 — 검사 예외 처리 */
        try {
            JsonNode root = mapper.readTree(content);   // JsonProcessingException, JsonMappingException
            return root.path("score").asInt(-1);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
    }
}

