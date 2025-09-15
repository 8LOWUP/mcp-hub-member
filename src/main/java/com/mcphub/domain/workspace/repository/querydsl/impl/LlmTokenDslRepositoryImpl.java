package com.mcphub.domain.workspace.repository.querydsl.impl;

import com.mcphub.domain.workspace.entity.LlmToken;
import com.mcphub.domain.workspace.entity.QLlmToken;
import com.mcphub.domain.workspace.entity.enums.Llm;
import com.mcphub.domain.workspace.repository.querydsl.LlmTokenDslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LlmTokenDslRepositoryImpl implements LlmTokenDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    QLlmToken llmToken = QLlmToken.llmToken;

    @Override
    public boolean existsByUserIdAndLlmId(Long userId, Llm llmId) {
        return jpaQueryFactory.selectOne()
                .from(llmToken)
                .where(llmToken.userId.eq(userId).and(llmToken.llmId.eq(llmId)))
                .fetchFirst() != null;
    }

    @Override
    public LlmToken findByUserIdANdLlmId(Long userId, Llm llmId) {
        return jpaQueryFactory.selectFrom(llmToken)
                .where(llmToken.userId.eq(userId).and(llmToken.llmId.eq(llmId)))
                .fetchOne();
    }
}
