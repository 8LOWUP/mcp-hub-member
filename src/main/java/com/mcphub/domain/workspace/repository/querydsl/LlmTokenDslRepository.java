package com.mcphub.domain.workspace.repository.querydsl;

import com.mcphub.domain.workspace.entity.LlmToken;
import com.mcphub.domain.workspace.entity.enums.Llm;

public interface LlmTokenDslRepository {
    boolean existsByUserIdAndLlmId(Long userId, Llm llmId);
    LlmToken findByUserIdANdLlmId(Long userId, Llm llmId);
}
