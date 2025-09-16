package com.mcphub.domain.mcp.adviser;

import com.mcphub.domain.mcp.converter.McpConverter;
import com.mcphub.domain.mcp.converter.McpDashboardConverter;
import com.mcphub.domain.mcp.dto.request.McpDraftRequest;
import com.mcphub.domain.mcp.dto.request.McpListRequest;
import com.mcphub.domain.mcp.dto.request.McpMetaDataRequest;
import com.mcphub.domain.mcp.dto.request.McpPublishRequest;
import com.mcphub.domain.mcp.dto.request.McpUrlRequest;
import com.mcphub.domain.mcp.dto.response.api.McpResponse;
import com.mcphub.domain.mcp.dto.response.api.MyUploadMcpDetailResponse;
import com.mcphub.domain.mcp.dto.response.readmodel.McpReadModel;
import com.mcphub.domain.mcp.service.mcpDashboard.McpDashboardService;
import com.mcphub.global.common.base.BaseResponse;
import com.mcphub.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor
public class McpDashboardAdviser {

	private final McpDashboardService mcpDashboardService;
	private final McpDashboardConverter mcpDashboardConverter;
	private final SecurityUtils securityUtils;

	public Page<McpResponse> getMyUploadMcpList(Pageable pageable, McpListRequest req) {
		Long userId = securityUtils.getUserId();
		Page<McpReadModel> page = mcpDashboardService.getMyUploadMcpList(pageable, req, userId);
		return page.map(mcpDashboardConverter::toMcpResponse);
	}

	public MyUploadMcpDetailResponse getUploadMcpDetail(Long mcpId) {
		Long userId = securityUtils.getUserId();
		return mcpDashboardConverter.toMyUploadMcpDetailResponse(
			mcpDashboardService.getUploadMcpDetail(userId, mcpId)
		);
	}

	public Long createMcpDraft(McpDraftRequest request) {
		Long userId = securityUtils.getUserId();
		return mcpDashboardService.createMcpDraft(userId, request);
	}

	public Long uploadMcpUrl(Long mcpId, McpUrlRequest request) {
		Long userId = securityUtils.getUserId();
		return mcpDashboardService.uploadMcpUrl(userId, mcpId, request);
	}

	public Long uploadMcpMetaData(Long mcpId, McpMetaDataRequest request) {
		Long userId = securityUtils.getUserId();
		return mcpDashboardService.uploadMcpMetaData(userId, mcpId, request);
	}

	public Long publishMcp(Long mcpId, McpPublishRequest request) {
		Long userId = securityUtils.getUserId();
		return mcpDashboardService.publishMcp(userId, mcpId, request);
	}

	public Long deleteMcp(Long mcpId) {
		Long userId = securityUtils.getUserId();
		return mcpDashboardService.deleteMcp(userId, mcpId);
	}
}
