// package com.mcphub.domain.member.client;
//
// import org.springframework.http.MediaType;
// import org.springframework.stereotype.Component;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.WebClient;
//
// import com.fasterxml.jackson.databind.JsonNode;
// import com.mcphub.domain.member.dto.response.readmodel.GoogleProfile;
//
// @Component
// public class GoogleOAuth2Client {
//
// 	private final WebClient webClient = WebClient.create();
//
// 	public GoogleProfile getProfile(String code) {
// 		String accessToken = webClient.post()
// 			.uri("https://oauth2.googleapis.com/token")
// 			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
// 			.body(BodyInserters.fromFormData("grant_type", "authorization_code")
// 				.with("client_id", "구글 클라이언트 ID")
// 				.with("client_secret", "구글 클라이언트 Secret")
// 				.with("redirect_uri", "http://localhost:8080/auth/google")
// 				.with("code", code))
// 			.retrieve()
// 			.bodyToMono(JsonNode.class)
// 			.block()
// 			.get("access_token").asText();
//
// 		return webClient.get()
// 			.uri("https://www.googleapis.com/oauth2/v2/userinfo")
// 			.header("Authorization", "Bearer " + accessToken)
// 			.retrieve()
// 			.bodyToMono(GoogleProfile.class)
// 			.block();
// 	}
// }
//
