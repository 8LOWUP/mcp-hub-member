package com.mcphub.domain.member.client;

import com.mcphub.domain.member.dto.client.SocialLoginResponse;
import com.mcphub.domain.member.entity.enums.LoginType;

/**
 * Social Login에서 구체적인 Client를 설정하는 interface
 * 구현체로 각 Social Login 마다 Specifical한 로직 구현
 */
public interface SocialMemberClient {
    SocialLoginResponse getSocialLoginResponse(final String accessToken) throws Exception;
    LoginType getLoginType();
}
