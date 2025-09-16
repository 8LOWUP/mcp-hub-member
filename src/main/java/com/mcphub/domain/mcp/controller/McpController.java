package com.mcphub.domain.mcp.controller;

import com.mcphub.domain.mcp.dto.request.MyUploadMcpRequest;
import com.mcphub.domain.mcp.dto.response.api.McpDetailResponse;
import com.mcphub.domain.mcp.dto.response.api.MySavedMcpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mcphub.domain.mcp.adviser.McpAdviser;
import com.mcphub.domain.mcp.dto.request.McpListRequest;
import com.mcphub.global.common.base.BaseResponse;
import com.mcphub.domain.mcp.dto.response.api.McpResponse;

/**
 * MCP 컨트롤러
 * - MCP 리스트 조회
 * - MCP 상세 조회
 * - MCP 구매/삭제
 * - 내가 등록한 MCP 조회
 */

@RestController
@RequestMapping("/mcps")
@RequiredArgsConstructor
public class McpController {
	private final McpAdviser mcpAdviser;

	/**
	 * 마켓 -> MCP 리스트
	 * @return 조건에 맞는 MCP 리스트
	 */
	@GetMapping()
	public BaseResponse<Page<McpResponse>> getMcpList(@ModelAttribute McpListRequest request) {
		Pageable pageable = PageRequest.of(
			request.getPage(),
			Math.min(request.getSize(), 50),
			Sort.by(Sort.Direction.DESC, "id")
		);

		return BaseResponse.onSuccess(
			mcpAdviser.getMcpList(pageable, request)
		);
	}

	/**
	 * MCP 상세 조회
	 * @param mcpId
	 * @return MCP 상세 내용
	 */
	@GetMapping("/{mcpId}")
	public BaseResponse<McpDetailResponse> getMcpDetail(@PathVariable Long mcpId) {
		return BaseResponse.onSuccess(mcpAdviser.getMcpDetail(mcpId));
	}

	/**
	 * MCPHub로 부터 저장
	 * @param mcpId
	 * @return MCP 상세 내용
	 */
	@PostMapping("/{mcpId}")
	public BaseResponse<Long> saveUserMcp(@PathVariable Long mcpId) {
		Long saveId = mcpAdviser.saveUserMcp(mcpId);
		return BaseResponse.onSuccess(saveId);
	}

	//TODO : 소프트 삭제? 하드 삭제? 여부 정해야함

	/**
	 * 구매한 MCP 삭제
	 * @param mcpId MCP ID
	 * @return 삭제 결과
	 */
	@DeleteMapping("/{mcpId}")
	public BaseResponse<Long> deleteMcp(@PathVariable Long mcpId) {
		Long deletedId = mcpAdviser.deleteMcp(mcpId);
		return BaseResponse.onSuccess(deletedId);
	}

	/**
	 * 등록한 MCP 리스트 (구매한)
	 * @return 등록한 MCP 리스트
	 */
	@GetMapping("/me")
	public BaseResponse<Page<MySavedMcpResponse>> getMySavedMcpList(@ModelAttribute MyUploadMcpRequest request) {
		Pageable pageable = PageRequest.of(
			request.getPage(),
			request.getSize(),
			Sort.by(Sort.Direction.DESC, "id")
		);

		return BaseResponse.onSuccess(mcpAdviser.getMySavedMcpList(pageable, request));
	}
}
