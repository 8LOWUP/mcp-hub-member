package com.mcphub.domain.mcp.adviser;

import com.mcphub.domain.mcp.dto.request.McpListRequest;
import com.mcphub.domain.mcp.dto.request.MyUploadMcpRequest;
import com.mcphub.domain.mcp.dto.response.api.McpDetailResponse;
import com.mcphub.domain.mcp.dto.response.api.McpResponse;
import com.mcphub.domain.mcp.dto.response.api.MyUploadMcpResponse;
import com.mcphub.domain.mcp.dto.response.readmodel.McpReadModel;
import com.mcphub.global.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.mcphub.domain.mcp.converter.McpConverter;
import com.mcphub.domain.mcp.dto.response.api.TestResponse;
import com.mcphub.domain.mcp.dto.response.readmodel.TestReadDto;
import com.mcphub.domain.mcp.service.mcp.McpService;

@Component
@RequiredArgsConstructor
public class McpAdviser {
	private final McpService mcpService;
	private final McpConverter mcpConverter;
	private final SecurityUtils securityUtils;

	public Page<McpResponse> getMcpList(Pageable pageable, McpListRequest req) {
		Page<McpReadModel> page = mcpService.getMcpList(pageable, req);
		return page.map(mcpConverter::toMcpResponse);
	}

	public McpDetailResponse getMcpDetail(Long id) {
		return mcpConverter.toMcpDetailResponse(mcpService.getMcpDetail(id));
	}

	public Long saveUserMcp(Long mcpId) {
		return mcpService.saveUserMcp(mcpId);
	}

	public Long deleteMcp(Long mcpId) {
		return mcpService.deleteMcp(mcpId);
	}

	public Page<MyUploadMcpResponse> getMyUploadMcpList(Pageable pageable, MyUploadMcpRequest req) {
		Long userId = securityUtils.getUserId();
		Page<McpReadModel> page = mcpService.getMyUploadMcpList(userId, pageable, req);
		return page.map(mcpConverter::toMyUploadMcpResponse);
	}
}
