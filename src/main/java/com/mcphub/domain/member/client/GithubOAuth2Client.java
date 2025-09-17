package com.mcphub.domain.member.client;

import com.mcphub.domain.member.client.properties.GithubOAuth2Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GithubOAuth2Client {

    private final WebClient webClient = WebClient.create();
    private final GithubOAuth2Properties githubOAuth2Properties;


}
