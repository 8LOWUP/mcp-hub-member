package com.mcphub.domain.workspace.entity;
import com.mcphub.global.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "workspace")
@Getter
@Setter
public class Workspace extends BaseEntity {

    @Id
    private String id;  // bigint -> String

    private String userId;  // bigint -> String
    private String llmId;   // bigint -> String
    private String title;   // text
}