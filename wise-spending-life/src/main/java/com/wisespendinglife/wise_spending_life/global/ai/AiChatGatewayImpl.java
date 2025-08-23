package com.wisespendinglife.wise_spending_life.global.ai;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.wisespendinglife.wise_spending_life.global.error.BusinessException;
import com.wisespendinglife.wise_spending_life.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AiChatGatewayImpl implements AiChatGateway{
    private final OpenAIClient openAI;

    @Override
    public String ai(String systemPrompt, String userPrompt) {

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model("gpt-5-nano")
                .addSystemMessage(systemPrompt)
                .addUserMessage(userPrompt)
                .temperature(1)
                .build();

        ChatCompletion completion = openAI.chat().completions().create(params);

        return completion.choices().stream()
                .findFirst()
                .flatMap(c -> c.message().content())
                .orElseThrow(() -> new BusinessException(ErrorCode.GPT_EMPTY_RESPONSE));
    }
}
