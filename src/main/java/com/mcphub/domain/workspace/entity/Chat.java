package com.mcphub.domain.workspace.entity;

import com.mcphub.global.common.base.BaseDocument;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@Setter
@Builder
public class Chat extends BaseDocument {

    @Transient
    public static final String SEQUENCE_NAME = "chat_sequence";

    @Id
    private Long id;
    private String workspaceId;
    private String chat;
    private boolean senderType;
}
