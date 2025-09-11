package com.mcphub.domain.member.service.auth;

import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.entity.enums.LoginType;
import com.mcphub.domain.member.dto.response.member.auth.MemberCreateTokenResponse;
import com.mcphub.domain.member.dto.response.member.common.MemberIdResponse;
import com.mcphub.domain.member.dto.response.member.auth.MemberLoginResponse;
import com.mcphub.domain.member.repository.querydsl.MemberDslRepository;
import com.mcphub.domain.member.strategy.context.LoginContext;
import com.mcphub.global.common.exception.RestApiException;
import com.mcphub.domain.member.status.AuthErrorStatus;
import com.mcphub.global.config.security.jwt.JwtProvider;
import com.mcphub.global.config.security.jwt.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberAuthService {
    public final MemberRefreshTokenServiceImpl refreshTokenService;
    public final MemberDslRepository memberDslRepository;

    public final JwtProvider jwtTokenProvider;
    private final LoginContext loginContext;

    // 소셜 로그인을 수행하는 함수
    @Override
    @Transactional
    public MemberLoginResponse socialLogin(String accessToken, LoginType loginType) {

        // 소셜 로그인 수행
        MemberLoginResponse response = loginContext.executeStrategy(accessToken, loginType);

        // 리프레쉬 토큰 저장
        refreshTokenService.saveRefreshToken(response.refreshToken(), response.memberId());

        return response;
    }

    // 새로운 액세스 토큰 발급 함수
    @Override
    @Transactional
    public MemberCreateTokenResponse generateNewAccessToken(String refreshToken, Member member){
        // 만료된 refreshToken인지 확인
        if (!jwtTokenProvider.validateToken(refreshToken))
            throw new RestApiException(AuthErrorStatus.EXPIRED_REFRESH_TOKEN);

        // 디비에 저장된 refreshToken과 동일하지 않다면 유효하지 않음
        if (!refreshTokenService.existRefreshToken(refreshToken, member.getId()))
            throw new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN);

        // 토큰 발행
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(member.getId().toString(), member.getRole().toString());

        // 리프레쉬 토큰 저장
        refreshTokenService.saveRefreshToken(tokenInfo.refreshToken(), member.getId());

        return new MemberCreateTokenResponse(member.getId(), tokenInfo.accessToken(), tokenInfo.refreshToken());
    }

    // 로그아웃 함수
    @Override
    @Transactional
    public MemberIdResponse logout(Member member) {

        // refreshToken 삭제
        refreshTokenService.deleteRefreshToken(member.getId());

        return new MemberIdResponse(member.getId());
    }

    // 회원 탈퇴 함수
    @Override
    @Transactional
    public MemberIdResponse withdrawal(Member member) {

        // refreshToken 삭제
        refreshTokenService.deleteRefreshToken(member.getId());

        // 멤버 soft delete
        member.delete();

        return new MemberIdResponse(member.getId());
    }

}
