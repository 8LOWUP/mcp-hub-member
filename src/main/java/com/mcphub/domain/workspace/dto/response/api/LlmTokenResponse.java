package com.mcphub.domain.workspace.dto.response.api;

import com.mcphub.domain.workspace.entity.enums.Llm;
import lombok.Builder;

import java.util.List;

@Builder
public record LlmTokenResponse(
        List<LlmTokenDto> llmTokens
) {
    @Builder
    public record LlmTokenDto(
            Llm llmId,
            String llmToken
    ) {
    }
}
