package umc.product.domain.member.service.auth;

import umc.product.domain.member.dto.response.member.auth.MemberCreateTokenResponse;
import umc.product.domain.member.dto.response.member.common.MemberIdResponse;
import umc.product.domain.member.dto.response.member.auth.MemberLoginResponse;
import umc.product.domain.member.entity.Member;
import umc.product.domain.member.entity.enums.LoginType;

public interface MemberAuthService {

    // 소셜 로그인
    MemberLoginResponse socialLogin(final String accessToken,
                                    LoginType loginType);
    // 새로운 액세스 토큰 발급
    MemberCreateTokenResponse generateNewAccessToken(String refreshToken,
                                                     Member member);
    // 로그아웃
    MemberIdResponse logout(Member member);
    // 회원 탈퇴
    MemberIdResponse withdrawal(Member member);

}
