package com.mcphub.domain.member.dto.response.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetailResponse {

    private String email;
    private String nickname;
}
