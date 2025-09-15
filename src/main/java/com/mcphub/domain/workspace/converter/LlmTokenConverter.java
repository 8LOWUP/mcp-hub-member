package com.mcphub.domain.workspace.converter;

import com.mcphub.domain.workspace.dto.response.api.LlmTokenResponse;
import com.mcphub.domain.workspace.entity.LlmToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LlmTokenConverter {
    public LlmTokenResponse toLlmTokenResponse(List<LlmToken> llmTokenPage) {
        List<LlmTokenResponse.LlmTokenDto> dtoList = llmTokenPage.stream()
                .map(llmToken -> LlmTokenResponse.LlmTokenDto.builder()
                        .llmId(llmToken.getLlmId())
                        .llmToken(llmToken.getToken())
                        .build())
                .collect(Collectors.toList());

        return LlmTokenResponse.builder()
                .llmTokens(dtoList)
                .build();
    }
}
