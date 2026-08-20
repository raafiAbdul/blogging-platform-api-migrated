package com.example.blogging_platform_api_migrated.dtos;

public final class PostRequestDto {
    private final String title;
    private final String content;
    private final String category;
    private final String[] tags;

    public PostRequestDto(String title, String content, String category, String[] tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public String[] getTags() {
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
