package com.mcphub.domain.mcp.entity;

import com.mcphub.global.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "article_mcp_tool")
public class ArticleMcpTool extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // FK(mcp_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mcp_id", nullable = false)
    private Mcp mcp;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
}
