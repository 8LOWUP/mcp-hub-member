package com.mcphub.domain.mcp.service.mcp;

import com.mcphub.domain.mcp.dto.request.McpListRequest;
import com.mcphub.domain.mcp.dto.request.MyUploadMcpRequest;
import com.mcphub.domain.mcp.dto.response.api.McpResponse;
import com.mcphub.domain.mcp.dto.response.readmodel.McpReadModel;
import com.mcphub.domain.mcp.dto.response.readmodel.TestReadDto;
import com.mcphub.domain.mcp.entity.Mcp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface McpService {
	Mcp addMcp();

	TestReadDto getMcpById(Long id);

	List<Mcp> getAllMcps();

	void deleteMcpById(Long id);

	Mcp updateMcp();

	McpReadModel getMcpDetail(Long id);

	Page<McpReadModel> getMcpList(Pageable pageable, McpListRequest request);

	Long saveUserMcp(Long mcpId);

	Long deleteMcp(Long mcpId);

	Page<McpReadModel> getMyUploadMcpList(Long userId, Pageable pageable, MyUploadMcpRequest request);
}
