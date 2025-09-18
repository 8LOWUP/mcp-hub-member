package com.mcphub.domain.member.adviser.member;

import com.mcphub.domain.member.client.GoogleOAuth2Client;
import com.mcphub.domain.member.dto.response.member.common.MemberIdResponse;
import com.mcphub.domain.member.dto.response.readmodel.GoogleProfile;
import com.mcphub.global.util.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mcphub.domain.member.client.KakaoOAuth2Client;
import com.mcphub.domain.member.converter.response.MemberResponseConverter;
import com.mcphub.domain.member.dto.response.api.SocialLoginResponse;
import com.mcphub.domain.member.dto.response.readmodel.KakaoProfile;
import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.domain.member.repository.redis.impl.MemberRedisRepositoryImpl;
import com.mcphub.domain.member.service.auth.port.MemberCommandPort;
import com.mcphub.global.config.security.jwt.JwtProvider;
import com.mcphub.global.config.security.jwt.TokenInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberAuthAdviser {

    private final MemberCommandPort memberCommandPort;
    private final JwtProvider jwtProvider;
    private final MemberResponseConverter responseConverter;
    private final KakaoOAuth2Client kakaoClient;
    private final GoogleOAuth2Client googleClient;
    private final MemberRedisRepositoryImpl redisRepository;
    private final SecurityUtils securityUtils;

    public SocialLoginResponse kakaoLogin(String code) {
        KakaoProfile profile = kakaoClient.getProfile(code);
        MemberRM member = memberCommandPort.saveOrUpdate(
                profile.getKakao_account().getEmail(),
                profile.getKakao_account().getProfile().getNickname()
        );

        TokenInfo token = jwtProvider.generateToken(member.id().toString());

        redisRepository.save(member.id(), token.refreshToken());

        return responseConverter.toSocialLoginResponse(token, member);
    }

    public SocialLoginResponse googleLogin(String code) {
        GoogleProfile profile = googleClient.getProfile(code);
        MemberRM member = memberCommandPort.saveOrUpdate(
                profile.getEmail(), profile.getName()
        );

        TokenInfo token = jwtProvider.generateToken(member.id().toString());

        redisRepository.save(member.id(), token.refreshToken());

        return responseConverter.toSocialLoginResponse(token, member);
    }

    public SocialLoginResponse regenerateToken(String refreshToken) {
        TokenInfo tokenInfo = memberCommandPort.reissueAccessToken(refreshToken);
        return responseConverter.toRegenerateTokenResponse(tokenInfo);
    }

    public Boolean logout() {
        // TODO: access token 블랙리스트 추가

        // TODO: refresh token 삭제

        // (optional) 현재 SecurityContextHolder 비우기
        SecurityContextHolder.clearContext();
    }

    public Boolean withdrawal() {
        // TODO: access token 블랙리스트 추가

        // TODO: refresh token 삭제

        // TODO: member 테이블의 유저 정보 deletedAt 추가

        // (optional) 현재 SecurityContextHolder 비우기
        SecurityContextHolder.clearContext();
    }
}

