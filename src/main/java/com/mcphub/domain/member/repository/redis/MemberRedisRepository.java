package com.mcphub.domain.member.repository.redis;

import java.util.Optional;

import io.jsonwebtoken.Claims;

public interface MemberRedisRepository {
    void save(Long memberId, String refreshToken);

    Optional<Long> findMemberIdByToken(String refreshToken);

    Boolean delete(String refreshToken);

    Boolean blockAccessToken(String accessToken, Claims claims);

    Boolean isTokenBlocked(String accessToken);
}
