package com.mcphub.domain.workspace.service;

import com.mcphub.domain.workspace.dto.request.CreateLlmTokenCommand;
import com.mcphub.domain.workspace.dto.request.UpdateLlmTokenCommand;
import com.mcphub.domain.workspace.dto.response.api.LlmTokenResponse;
import com.mcphub.domain.workspace.entity.LlmToken;

import java.util.List;

public interface LlmTokenService {
    List<LlmToken> get(Long userId);
    LlmTokenResponse create(CreateLlmTokenCommand cmd);
    LlmTokenResponse update(UpdateLlmTokenCommand cmd);
}
