package com.mcphub.domain.member.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.mcphub.domain.member.client.properties.GithubOAuth2Properties;
import com.mcphub.domain.member.dto.response.readmodel.GithubProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class GithubOAuth2Client {

    private final WebClient webClient = WebClient.create();
    private final GithubOAuth2Properties githubOAuth2Properties;

    public GithubProfile getProfile(String code) {
        String accessToken = webClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .headers(headers -> {
                    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));  // JSON 응답 받기
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);             // 요청 body 타입
                })
                .body(BodyInserters.fromFormData("client_id", githubOAuth2Properties.getClientId())
                        .with("client_secret", githubOAuth2Properties.getClientSecret())
                        .with("redirect_uri", githubOAuth2Properties.getRedirectUri())
                        .with("code", code))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block()
                .get("access_token").asText();

        return webClient.get()
                .uri("https://api.github.com/user")
                .header("Authorization", "token " + accessToken)
                .headers(headers -> headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)))
                .retrieve()
                .bodyToMono(GithubProfile.class)
                .block();

    }
}
