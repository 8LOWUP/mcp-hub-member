package com.mcphub.domain.mcp.controller;

import com.mcphub.domain.mcp.adviser.McpDashboardAdviser;
import com.mcphub.domain.mcp.dto.request.McpListRequest;
import com.mcphub.domain.mcp.dto.response.api.CategoryResponse;
import com.mcphub.domain.mcp.dto.response.api.LicenseResponse;
import com.mcphub.domain.mcp.dto.response.api.McpResponse;
import com.mcphub.domain.mcp.dto.response.api.MyUploadMcpDetailResponse;
import com.mcphub.domain.mcp.dto.response.api.PlatformResponse;
import com.mcphub.domain.mcp.service.mcpDashboard.McpDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mcphub.domain.mcp.dto.request.McpDraftRequest;
import com.mcphub.domain.mcp.dto.request.McpMetaDataRequest;
import com.mcphub.domain.mcp.dto.request.McpPublishRequest;
import com.mcphub.domain.mcp.dto.request.McpUrlRequest;
import com.mcphub.global.common.base.BaseResponse;

@RestController
@RequestMapping("/mcps/dashboard")
@RequiredArgsConstructor
public class McpDashboardController {

	private final McpDashboardAdviser mcpDashboardAdviser;

	@GetMapping()
	public BaseResponse<Page<McpResponse>> getMyUploadMcpList(@ModelAttribute McpListRequest request) {
		Pageable pageable = PageRequest.of(
			request.getPage(),
			Math.min(request.getSize(), 50),
			Sort.by(Sort.Direction.DESC, "id")
		);

		return BaseResponse.onSuccess(
			mcpDashboardAdviser.getMyUploadMcpList(pageable, request)
		);
	}

	/**
	 * 업로드한 Mcp 상세
	 * @return Mcp 상세 (provider)
	 */
	@GetMapping("/{mcpId}")
	public BaseResponse<MyUploadMcpDetailResponse> getUploadMcpDetail(@PathVariable Long mcpId) {
		return BaseResponse.onSuccess(mcpDashboardAdviser.getUploadMcpDetail(mcpId));
	}

	/**
	 * MCP 등록 드래프트 생성
	 * @return 드래프트 생성
	 */
	@PostMapping()
	public BaseResponse<Long> createMCPDraft(@RequestBody McpDraftRequest request) {
		Long mcpId = mcpDashboardAdviser.createMcpDraft(request);
		return BaseResponse.onSuccess(mcpId);
	}

	@GetMapping("/category")
	public BaseResponse<CategoryResponse> getCategories() {
		return BaseResponse.onSuccess(new CategoryResponse());
	}

	@GetMapping("/license")
	public BaseResponse<LicenseResponse> getLicenses() {
		return BaseResponse.onSuccess(new LicenseResponse());
	}

	@GetMapping("/platform")
	public BaseResponse<PlatformResponse> getPlatforms() {
		return BaseResponse.onSuccess(new PlatformResponse());
	}

	/**
	 * MCP Url 업로드
	 * @return url 등록
	 */
	@PatchMapping("/{mcpId}/url")
	public BaseResponse<Long> uploadMCPUrl(@PathVariable Long mcpId, @RequestBody McpUrlRequest request) {
		return BaseResponse.onSuccess(mcpDashboardAdviser.uploadMcpUrl(mcpId, request));
	}

	/**
	 * MCP 메타 데이터 등록
	 * @return Mcp 메타 데이터 등록
	 */
	@PatchMapping("/{mcpId}/meta")
	public BaseResponse<Long> uploadMcpMetaData(@PathVariable Long mcpId,
	                                            @RequestBody McpMetaDataRequest request) {
		Long updateMcpId = mcpDashboardAdviser.uploadMcpMetaData(mcpId, request);
		return BaseResponse.onSuccess(updateMcpId);
	}

	/**
	 * MCP 최종 배포
	 * @return Mcp 최종 배포
	 */
	@PatchMapping("/{mcpId}/publish")
	public BaseResponse<Long> publishMcp(@PathVariable Long mcpId, @RequestBody McpPublishRequest request) {
		Long publishedMcpId = mcpDashboardAdviser.publishMcp(mcpId, request);
		return BaseResponse.onSuccess(publishedMcpId);
	}

	/**
	 * MCP 삭제 (provider)
	 * @param mcpId
	 * @return MCP 삭제
	 */
	@DeleteMapping("{mcpId}")
	public BaseResponse<Long> deleteMcp(@PathVariable Long mcpId) {
		Long deletedMcpId = mcpDashboardAdviser.deleteMcp(mcpId);
		return BaseResponse.onSuccess(deletedMcpId);
	}
}
