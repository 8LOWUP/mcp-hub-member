package com.mcphub.domain.member.adviser.member;

import com.mcphub.domain.member.client.GithubOAuth2Client;
import com.mcphub.domain.member.client.GoogleOAuth2Client;
import com.mcphub.domain.member.dto.response.readmodel.GithubProfile;
import com.mcphub.domain.member.dto.response.readmodel.GoogleProfile;
import com.mcphub.domain.member.service.auth.port.MemberQueryPort;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.mcphub.domain.member.client.KakaoOAuth2Client;
import com.mcphub.domain.member.converter.response.MemberResponseConverter;
import com.mcphub.domain.member.dto.response.api.SocialLoginResponse;
import com.mcphub.domain.member.dto.response.readmodel.KakaoProfile;
import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.global.token.repository.redis.impl.MemberRedisRepositoryImpl;
import com.mcphub.domain.member.service.auth.port.MemberCommandPort;
import com.mcphub.global.config.security.jwt.JwtProvider;
import com.mcphub.global.config.security.jwt.TokenInfo;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MemberAuthAdviser {

    private final MemberCommandPort memberCommandPort;
    private final JwtProvider jwtProvider;
    private final MemberResponseConverter responseConverter;
    private final KakaoOAuth2Client kakaoClient;
    private final GoogleOAuth2Client googleClient;
    private final GithubOAuth2Client githubClient;
    private final MemberRedisRepositoryImpl redisRepository;
    private final MemberQueryPort memberQueryPort;

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

    public SocialLoginResponse githubLogin(String code) {
        GithubProfile profile = githubClient.getProfile(code);
        MemberRM member = memberCommandPort.saveOrUpdate(
                profile.getLogin(), profile.getId()
        );

        TokenInfo token = jwtProvider.generateToken(member.id().toString());

        redisRepository.save(member.id(), token.refreshToken());

        return responseConverter.toSocialLoginResponse(token, member);
    }

    public SocialLoginResponse regenerateToken(String refreshToken) {
        TokenInfo tokenInfo = memberCommandPort.reissueAccessToken(refreshToken);
        return responseConverter.toRegenerateTokenResponse(tokenInfo);
    }

    public Boolean logout(HttpServletRequest request, String refreshToken) {
        // refresh token 삭제
        Boolean refreshTokenDeleted = memberCommandPort.deleteRefreshToken(refreshToken);

        // access token 가져오기
        String accessToken = jwtProvider.resolveToken(request);
        Claims claims = jwtProvider.getClaims(accessToken);

        // access token 블랙리스트 추가
        Boolean accessTokenBlocked = memberCommandPort.blockAccessToken(accessToken, claims);

        // (optional) 현재 SecurityContextHolder 비우기
        SecurityContextHolder.clearContext();

        return refreshTokenDeleted && accessTokenBlocked && Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
    }

    public Boolean withdrawal(HttpServletRequest request, String refreshToken) {

        // member 테이블의 유저 정보 deletedAt 추가
        Boolean memberDeleted = memberCommandPort.memberWithdrawal(memberQueryPort.findByRefreshToken(refreshToken));

        // refresh token 삭제
        Boolean refreshTokenDeleted = memberCommandPort.deleteRefreshToken(refreshToken);

        // access token 가져오기
        String accessToken = jwtProvider.resolveToken(request);
        Claims claims = jwtProvider.getClaims(accessToken);

        // access token 블랙리스트 추가
        Boolean accessTokenBlocked = memberCommandPort.blockAccessToken(accessToken, claims);

        // (optional) 현재 SecurityContextHolder 비우기
        SecurityContextHolder.clearContext();

        return refreshTokenDeleted && accessTokenBlocked && memberDeleted && Objects.isNull(SecurityContextHolder.getContext().getAuthentication());
    }
}

