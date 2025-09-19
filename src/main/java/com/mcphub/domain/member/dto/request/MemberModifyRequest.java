package com.mcphub.domain.member.dto.request;

import lombok.Getter;

@Getter
public class MemberModifyRequest {
    Long id;
    String email;
    String nickname;
}
