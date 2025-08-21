package com.wisespendinglife.wise_spending_life.domain.recommendation.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.wisespendinglife.wise_spending_life.domain.challenge.entity.ChallengeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;



@Component
@RequiredArgsConstructor
public class OpenAIRecommendationClient {
    private static final String DEFAULT_MODEL = "gpt-5-nano";
    private static final Set<ChallengeType> CHALLENGE_TYPES = EnumSet.of(ChallengeType.PAY_NOT, ChallengeType.PAY_LESS, ChallengeType.PAY_SAVE);

    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /* LLM 단일 아이템 */
    public record LLMItem(String challengeName, ChallengeType challengeType,
                          Long challengeDays,List<String> categories) {}
    /* LLM 전체 응답 */
    public record LLMResult(List<LLMItem> recommendations) {}

    /*
    * @param user_id 유저 ID
    * @param pays PaymentMiniDto [{"transactionAt":"yyyy-MM-dd", "category":"식비"}, ...]
    * @param model DEFAULT_MODEL
    */

    public LLMResult generate(Long user_id, List<Map<String, Object>> pays, String model){
        // 입력 카테고리 집합 (신규 생성 금지 용도)
        Set<String> inputCats = pays.stream()
                .map(m -> String.valueOf(m.getOrDefault("category", "")).trim())
                .filter(s -> !s.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        String systemPrompt = """
            당신은 사용자의 소비 카테고리를 바탕으로 절약/소비 제어 챌린지를 추천하는 전문가입니다.
            반드시 아래 JSON 구조만 출력하세요. 설명, 코드펜스, 불필요한 텍스트 금지.

            제약:
            - challengeType ∈ {"pay_not","pay_less","pay_save"}.
            - 각 recommendation.categories는 입력으로 제공된 카테고리 집합 안에서만 선택. 새로운 카테고리 금지.
            - challengeDays는 {3,7,14,30} 중 하나의 정수.
            - 한국어 간결 표현.
            - categories는 최소 1개, 최대 8개.
            - 최소 1개, 최대 5개 추천.

            출력 JSON 예:
            {
              "recommendations": [
                {
                  "challengeName": "식비 줄이기",
                  "challengeType": "pay_less",
                  "challengeDays": 7,
                  "categories": ["식비"]
                }
              ]
            }
            """;

        List<Map<String, Object>> payMaps = pays.stream()
                .map(m -> Map.<String, Object>of(
                        "transactionAt", String.valueOf(m.get("transactionAt")),
                        "category", String.valueOf(m.get("category")).trim()
                ))
                .collect(Collectors.toList());

        Map<String, Object> userPayload = Map.of(
                "user_id", user_id,
                "categories_allowed", inputCats,
                "pays", payMaps
        );

        final String userPrompt;
        try{
            userPrompt = objectMapper.writeValueAsString(userPayload);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model((model == null || model.isBlank()) ? DEFAULT_MODEL : model)
                .addSystemMessage(systemPrompt)
                .addUserMessage(userPrompt)
                .temperature(1)
                .build();

        final ChatCompletion completion;
        try {
            completion = openAIClient.chat().completions().create(params);
        } catch (Exception e) {
            throw new RuntimeException("OpenAI call failed", e);
        }

        String raw = completion.choices().stream()
                .findFirst()
                .flatMap(c -> c.message().content())
                .map(OpenAIRecommendationClient::stripFence)
                .orElseThrow(() -> new RuntimeException("Empty content from OpenAI"));

        final LLMResult parsed;
        try {
            parsed = objectMapper.readValue(raw, new TypeReference<LLMResult>() {});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse OpenAI JSON", e);
        }

        List<LLMItem> cleaned = sanitize(parsed.recommendations(), inputCats);
        if (cleaned.isEmpty()) {
            throw new RuntimeException("No valid recommendations after validation");
        }
        return new LLMResult(cleaned);
    }

    private static String stripFence(String s) {
        String t = s == null ? "" : s.trim();
        if (t.startsWith("```")) {
            int nl = t.indexOf('\n');
            if (nl > 0) t = t.substring(nl + 1);
            int tail = t.lastIndexOf("```");
            if (tail >= 0) t = t.substring(0, tail);
        }
        return t.trim();
    }

    private static List<LLMItem> sanitize(List<LLMItem> items, Set<String> allowedCats) {
        if (items == null) return List.of();
        return items.stream()
                .map(i -> {
                    if (i == null) return null;

                    // enum 체크
                    if (i.challengeType() == null || !CHALLENGE_TYPES.contains(i.challengeType())) return null;

                    long days = i.challengeDays() == null ? 0L : i.challengeDays();
                    if (days < 3 || days > 30) return null;

                    List<String> cats = Optional.ofNullable(i.categories()).orElse(List.of()).stream()
                            .map(s -> s == null ? "" : s.trim())
                            .filter(s -> !s.isBlank() && allowedCats.contains(s))
                            .distinct()
                            .limit(8) // 최대 8개
                            .collect(Collectors.toList());
                    if (cats.isEmpty()) return null;

                    String name = i.challengeName() == null ? "" : i.challengeName().trim();
                    if (name.isBlank()) return null;

                    return new LLMItem(name, i.challengeType(), days, cats);
                })
                .filter(Objects::nonNull)
                .limit(5)
                .collect(Collectors.toList());
    }
}
