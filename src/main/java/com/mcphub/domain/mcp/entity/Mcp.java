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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.mcphub.global.common.base.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "mcp")
public class Mcp extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "platform_id", nullable = true)
	private Platform platform;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = true)
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "license_id", nullable = true)
	private License license;

	@Column(nullable = true, columnDefinition = "text")
	private String name;

	@Column(nullable = true, columnDefinition = "text")
	private String version;

	@Column(name = "request_url", nullable = true, columnDefinition = "text")
	private String requestUrl;

	@Column(nullable = true, columnDefinition = "text")
	private String description;

	@Column(name = "source_url", columnDefinition = "text")
	private String sourceUrl;

	@Column(name = "image_url", columnDefinition = "text")
	private String imageUrl;

	@Column(name = "is_key_required", nullable = true)
	private Boolean isKeyRequired;

	@Column(name = "is_published", nullable = true)
	private Boolean isPublished;

	// 최초 publsh한 날짜
	@Column(name = "publishedAt", nullable = true)
	private LocalDateTime publishedAt;

	// 최종 publish 날짜
	@Column(name = "lastPublishAt", nullable = true)
	private LocalDateTime lastPublishAt;
}
