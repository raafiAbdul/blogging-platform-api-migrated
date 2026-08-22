package com.example.blogging_platform_api_migrated.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public final class PostRequestDto {

    @NotNull
    @Size(min = 3, max = 255)
    private final String title;

    @NotNull
    @Size(min = 3, max = 255)
    private final String content;

    @NotNull
    @Size(min = 3, max = 255)
    private final String category;
    private final Set<String> tags;

    public PostRequestDto(String title, String content, String category, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
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
}
