package com.mcphub.domain.workspace.entity;

import com.mcphub.domain.workspace.common.McpInfo;
import com.mcphub.global.common.base.BaseDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "workspace")
@Getter
@Setter
@Builder
public class Workspace extends BaseDocument {

    @Transient
    public static final String SEQUENCE_NAME = "workspace_sequence";

    @Id
    private Long id;
    private String llmId;   // bigint -> String
    private String userId;  // bigint -> String
    private String title;   // text

    private List<McpInfo> mcps = new ArrayList<>();
}