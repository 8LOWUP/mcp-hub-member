package com.mcphub.domain.member.client.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.github")
@Getter
@Setter
public class GithubOAuth2Properties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
}
