package com.mcphub.domain.member.service.auth.impl;

import java.util.Objects;
import java.util.Optional;

import com.mcphub.domain.member.status.MemberErrorStatus;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.repository.Jpa.MemberRepository;
import com.mcphub.global.token.repository.redis.RedisRepository;
import com.mcphub.domain.member.service.auth.port.MemberCommandPort;
import com.mcphub.domain.member.service.auth.port.MemberQueryPort;
import com.mcphub.global.common.exception.code.status.AuthErrorStatus;
import com.mcphub.global.common.exception.RestApiException;
import com.mcphub.global.config.security.jwt.JwtProvider;
import com.mcphub.global.config.security.jwt.TokenInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberAuthServiceImpl implements MemberCommandPort, MemberQueryPort {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RedisRepository redisRepository;

    @Override
    @Transactional
    public MemberRM saveOrUpdate(String email, String nickname) {

        Member member = memberRepository.findFirstByEmailAndDeletedAtIsNullOrderByUpdatedAtDescCreatedAtDesc(email)
                .orElseGet(() -> Member.builder() // 데이터가 없다면 새로 삽입
                                .email(email)
                                .nickname(nickname)
                                .build()
                );

        memberRepository.save(member);
        return new MemberRM(member.getId(), member.getEmail(), member.getNickname());
    }

    @Override
    public Optional<MemberRM> findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(m -> new MemberRM(m.getId(), m.getEmail(), m.getNickname()));
    }

    // 토큰 재발급
    @Override
    @Transactional
    public TokenInfo reissueAccessToken(String refreshToken) {
        // 1. refresh token 존재 여부 검증
        Long memberId = redisRepository.findMemberIdByToken(refreshToken)
                .orElseThrow(() -> new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN));

        // 2. 기존 refresh token 제거 (rotation)
        redisRepository.delete(refreshToken);

        // 3. 새로운 access & refresh token 발급
        TokenInfo tokenInfo = jwtProvider.generateToken(memberId.toString());

        // 4. 새 refresh token Redis 저장
        redisRepository.save(memberId, tokenInfo.refreshToken()); // 2주

        return tokenInfo;
    }


    // refresh token으로 member id 조회
    @Override
    @Transactional
    public Long findByRefreshToken(String refreshToken) {
        return redisRepository.findMemberIdByToken(refreshToken)
                .orElseThrow(() -> new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN));
    }

    // refresh token 삭제
    @Override
    @Transactional
    public Boolean deleteRefreshToken(String refreshToken) {

        Long memberId = redisRepository.findMemberIdByToken(refreshToken)
                .orElseThrow(() -> new RestApiException(AuthErrorStatus.INVALID_REFRESH_TOKEN));

        return redisRepository.delete(refreshToken);
    }

    // 현재 처리중인 요청의 액세스 토큰을 블랙리스트에 추가
    @Override
    @Transactional
    public Boolean blockAccessToken(String accessToken, Claims claims) {
        return redisRepository.blockAccessToken(accessToken, claims);
    }

    // 해당 member 데이터를 soft delete
    @Override
    @Transactional
    public Boolean memberWithdrawal(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new RestApiException(MemberErrorStatus.MEMBER_NOT_FOUND));

        member.delete();

        return !Objects.isNull(memberRepository.save(member).getDeletedAt());
    }
}

