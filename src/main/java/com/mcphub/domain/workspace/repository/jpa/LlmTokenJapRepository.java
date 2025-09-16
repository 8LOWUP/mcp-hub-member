package com.mcphub.domain.workspace.repository.jpa;

import com.mcphub.domain.workspace.entity.LlmToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LlmTokenJapRepository extends JpaRepository<LlmToken, Long> {
    List<LlmToken> findByUserId(Long userId);
}
