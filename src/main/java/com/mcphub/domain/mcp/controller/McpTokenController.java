package com.mcphub.domain.mcp.controller;

import com.mcphub.domain.mcp.adviser.McpTokenAdviser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mcphub.domain.mcp.dto.request.McpTokenRequest;
import com.mcphub.global.common.base.BaseResponse;

/**
 * MCP 토큰 관리 컨트롤러
 * MCP 토큰 저장 및 수정 기능을 제공합니다.
 */

@RestController
@RequestMapping("/mcps/token")
@RequiredArgsConstructor
public class McpTokenController {

	private final McpTokenAdviser mcpTokenAdviser;

	/**
	 * MCP 토큰 저장
	 * @return 토큰 저장 성공
	 */
	@PostMapping("/{mcpId}")
	public BaseResponse<Long> saveMcpToken(@PathVariable Long mcpId, @RequestBody McpTokenRequest request) {
		Long savedTokenMcpId = mcpTokenAdviser.saveMcpToken(mcpId, request);
		return BaseResponse.onSuccess(savedTokenMcpId);
	}

	/**
	 * MCP 토큰 수정
	 * @return 토큰 수정 성공
	 */
	@PatchMapping("/{mcpId}")
	public BaseResponse<Long> updateMcpToken(@PathVariable Long mcpId, @RequestBody McpTokenRequest request) {
		Long updatedTokenMcpId = mcpTokenAdviser.updateMcpToken(mcpId, request);
		return BaseResponse.onSuccess(updatedTokenMcpId);
	}
}
