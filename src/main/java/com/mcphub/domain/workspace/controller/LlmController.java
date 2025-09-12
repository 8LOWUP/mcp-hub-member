package com.mcphub.domain.workspace.controller;

import com.mcphub.domain.workspace.adviser.LlmAdviser;
import com.mcphub.domain.workspace.dto.request.LlmTokenRequest;
import com.mcphub.domain.workspace.dto.response.LlmResponse;
import com.mcphub.domain.workspace.dto.response.LlmTokenResponse;
import com.mcphub.domain.workspace.entity.enums.Llm;
import com.mcphub.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "LLM Token API", description = "사용자의 LLM Token을 저장하고 수정하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/llm")
public class LlmController {
    private final LlmAdviser llmAdviser;

    @GetMapping(path = "")
    public BaseResponse<List<LlmResponse>> getLlmList() {
        return BaseResponse.onSuccess(llmAdviser.getLlmList());
    }

    @GetMapping(path = "/token/{llmId}")
    public BaseResponse<LlmTokenResponse> getToken(
            @PathVariable("llmId") Llm llmId
    ) {
        return BaseResponse.onSuccess(llmAdviser.getToken(llmId));
    }

    @Operation(summary = "사용자 LLM Token 입력 API", description = "사용자의 LLM Token을 입력받아 저장하는 API 입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "LLM Token 저장 성공"
            )
    })
    @Parameters({
            @Parameter(name = "llmId", description = "LLM의 ID 값"),
            @Parameter(name = "llmToken", description = "사용자의 LLM Token값"),
    })
    @PostMapping(path = "/token")
    public BaseResponse<LlmTokenResponse> registerToken(
            @RequestHeader(value = "accessToken") String accessToken,
            @RequestBody LlmTokenRequest request
    ) {

        return BaseResponse.onSuccess(llmAdviser.registerToken(request));
    }

    @Operation(summary = "사용자 LLM Token 수정 API", description = "사용자의 LLM Token을 입력받아 수정하는 API 입니다")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "LLM Token 수정 성공"
            )
    })
    @Parameters({
            @Parameter(name = "llmId", description = "LLM의 ID 값"),
            @Parameter(name = "llmToken", description = "사용자의 LLM Token값"),
    })
    @PatchMapping(path = "/token")
    public BaseResponse<LlmTokenResponse> updateToken(
            @RequestBody LlmTokenRequest request
    ) {
        return BaseResponse.onSuccess(llmAdviser.updateToken(request));
    }
}
