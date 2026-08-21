package com.example.blogging_platform_api_migrated.dtos;

import java.time.OffsetDateTime;
import java.util.Set;

public class PostResponseDto {
    private final int id;
    private final String title;
    private final String content;
    private final String category;
    private final Set<String> tags;
    private final OffsetDateTime createdAt;
    private final OffsetDateTime updatedAt;

    public PostResponseDto(
            int id,  String title,
            String content, String category,
            Set<String> tags,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Set<String> getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }
}
