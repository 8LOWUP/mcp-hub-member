package com.mcphub.domain.member.dto.response.readmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubProfile {

    private String id;
    private String login;
    private String avatar_url;
}
