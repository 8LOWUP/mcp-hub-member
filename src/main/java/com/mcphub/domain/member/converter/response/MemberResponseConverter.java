package com.mcphub.domain.member.converter.response;

import com.mcphub.domain.member.dto.response.api.MemberDetailResponse;
import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.entity.MemberElasticDocument;
import org.springframework.stereotype.Component;

import com.mcphub.domain.member.dto.response.api.SocialLoginResponse;
import com.mcphub.domain.member.dto.response.readmodel.MemberRM;
import com.mcphub.global.config.security.jwt.TokenInfo;

@Component
public class MemberResponseConverter {
    public SocialLoginResponse toSocialLoginResponse(TokenInfo token, MemberRM member) {
        return new SocialLoginResponse(token.accessToken(), token.refreshToken(), member);
    }

    public SocialLoginResponse toRegenerateTokenResponse(TokenInfo token) {
        return new SocialLoginResponse(token.accessToken(), token.refreshToken());
    }

    public MemberDetailResponse toMemberElasticSearchResponse(MemberElasticDocument searchedMember) {
        return new MemberDetailResponse(searchedMember.getEmail(),  searchedMember.getNickname());
    }

    public MemberDetailResponse toMemberDetailResponse(Member member) {
        return new MemberDetailResponse(member.getEmail(), member.getNickname());
    }
}


