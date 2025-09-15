package com.mcphub.domain.workspace.service;

import com.mcphub.domain.workspace.dto.request.CreateLlmTokenCommand;
import com.mcphub.domain.workspace.dto.request.UpdateLlmTokenCommand;
import com.mcphub.domain.workspace.dto.response.api.LlmTokenResponse;
import com.mcphub.domain.workspace.entity.LlmToken;
import com.mcphub.domain.workspace.repository.jpa.LlmTokenJapRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LlmTokenServiceImpl implements LlmTokenService {
    private final LlmTokenJapRepository llmTokenJapRepository;

    @Transactional
    public List<LlmToken> get(Long userId) {
        return llmTokenJapRepository.findByUserId(userId);
    }

    @Transactional
    public LlmTokenResponse create(CreateLlmTokenCommand cmd) {
        LlmToken llmToken = new LlmToken(null, cmd.userId(), cmd.llmId(), cmd.llmToken());
        //이미 유저가 등록한지 확인
        //DB에 저장
        return null;
    }

    @Transactional
    public LlmTokenResponse update(UpdateLlmTokenCommand cmd) {
        //유저가 등록한 기록이 있는지 확인
        //DB 업데이트
        return null;
    }
}
