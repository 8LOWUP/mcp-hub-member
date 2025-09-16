package com.mcphub.domain.workspace.service;

import com.mcphub.domain.workspace.dto.request.CreateLlmTokenCommand;
import com.mcphub.domain.workspace.dto.request.UpdateLlmTokenCommand;
import com.mcphub.domain.workspace.dto.response.api.LlmTokenListResponse;
import com.mcphub.domain.workspace.entity.LlmToken;
import com.mcphub.domain.workspace.repository.jpa.LlmTokenJapRepository;
import com.mcphub.domain.workspace.repository.querydsl.LlmTokenDslRepository;
import com.mcphub.domain.workspace.status.LlmErrorStatus;
import com.mcphub.global.common.exception.RestApiException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LlmTokenServiceImpl implements LlmTokenService {
    private final LlmTokenJapRepository llmTokenJapRepository;
    private final LlmTokenDslRepository llmTokenDslRepository;
    private final StringEncryptor stringEncryptor;

    @Transactional
    public List<LlmToken> get(Long userId) {
        List<LlmToken> llmTokenList = llmTokenJapRepository.findByUserId(userId);

        return llmTokenList.stream()
                .peek(llmToken -> {
                    String decryptedToken = stringEncryptor.decrypt(llmToken.getToken());
                    llmToken.setToken(decryptedToken);
                })
                .toList();
    }

    @Transactional
    public LlmToken create(CreateLlmTokenCommand cmd) {
        //유저가 과거에 등록한적 있는지 확인
        if (llmTokenDslRepository.existsByUserIdAndLlmId(cmd.userId(), cmd.llmId()))
            throw new RestApiException(LlmErrorStatus.TOKEN_ALREADY_EXISTS);

        String encryptedToken = stringEncryptor.encrypt(cmd.llmToken());

        return llmTokenJapRepository.save(new LlmToken(null, cmd.userId(), cmd.llmId(), encryptedToken));
    }

    @Transactional
    public LlmToken update(UpdateLlmTokenCommand cmd) {
        //유저가 등록한 기록이 있는지 확인
        if(!llmTokenDslRepository.existsByUserIdAndLlmId(cmd.userId(), cmd.llmId()))
            throw new RestApiException(LlmErrorStatus.TOKEN_NOT_EXISTS);

        //DB 업데이트
        LlmToken llmToken = llmTokenDslRepository.findByUserIdANdLlmId(cmd.userId(), cmd.llmId());
        String encryptedToken = stringEncryptor.encrypt(cmd.llmToken());
        llmToken.setToken(encryptedToken);

        return llmToken;
    }
}
