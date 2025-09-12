package com.mcphub.domain.mcp.converter;

import com.mcphub.domain.mcp.dto.response.api.McpDetailResponse;
import com.mcphub.domain.mcp.dto.response.api.McpResponse;
import com.mcphub.domain.mcp.dto.response.api.MyUploadMcpResponse;
import com.mcphub.domain.mcp.dto.response.readmodel.McpReadModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.mcphub.domain.mcp.dto.response.api.TestResponse;

@Component
public class McpConverter {

	public TestResponse toTestResponse(Long id) {
		return TestResponse.builder()
		                   .id(id)
		                   .build();
	}

	public McpResponse toMcpResponse(McpReadModel m) {
		return McpResponse.builder()
		                  .id(m.getId())
		                  .name(m.getName())
		                  .version(m.getVersion())
		                  .description(m.getDescription())
		                  .imageUrl(m.getImageUrl())
		                  .isKeyRequired(m.getIsKeyRequired())
		                  .categoryName(m.getCategoryName())
		                  .platformName(m.getPlatformName())
		                  .licenseName(m.getLicenseName())
		                  .averageRating(m.getAverageRating())
		                  .savedUserCount(m.getSavedUserCount())
		                  .publishedDate(m.getPublishedAt())
		                  .build();
	}

	public McpDetailResponse toMcpDetailResponse(McpReadModel m) {
		return McpDetailResponse.builder()
		                        .id(m.getId())
		                        .name(m.getName())
		                        .version(m.getVersion())
		                        .description(m.getDescription())
		                        .imageUrl(m.getImageUrl())
		                        .isKeyRequired(m.getIsKeyRequired())
		                        .categoryName(m.getCategoryName())
		                        .platformName(m.getPlatformName())
		                        .licenseName(m.getLicenseName())
		                        .averageRating(m.getAverageRating())
		                        .savedUserCount(m.getSavedUserCount())
		                        .tools(m.getTools())
		                        .build();
	}

	public MyUploadMcpResponse toMyUploadMcpResponse(McpReadModel m) {
		return MyUploadMcpResponse.builder()
		                          .id(m.getId())
		                          .name(m.getName())
		                          .version(m.getVersion())
		                          .description(m.getDescription())
		                          .imageUrl(m.getImageUrl())
		                          .categoryName(m.getCategoryName())
		                          .platformName(m.getPlatformName())
		                          .licenseName(m.getLicenseName())
		                          .build();
	}
}
