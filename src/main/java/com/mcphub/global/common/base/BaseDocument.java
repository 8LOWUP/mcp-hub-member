package com.mcphub.global.common.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public abstract class BaseDocument {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    // 삭제 여부 확인 메서드
    public boolean isDeleted() {
        return deletedAt != null;
    }

    // 삭제 처리 메서드
    public void delete() {
        deletedAt = LocalDateTime.now();
    }
}
