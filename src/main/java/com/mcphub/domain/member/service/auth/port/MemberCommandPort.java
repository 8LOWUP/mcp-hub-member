package com.mcphub.domain.member.service.auth.port;

import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.global.config.security.jwt.TokenInfo;
import io.jsonwebtoken.Claims;

public interface MemberCommandPort {
    MemberRM saveOrUpdate(String email, String nickname);

    TokenInfo reissueAccessToken(String refreshToken);

    Boolean deleteRefreshToken(String refreshToken);

    Boolean blockAccessToken(String accessToken, Claims claims);

    Boolean memberWithdrawal(Long memberId);
}
