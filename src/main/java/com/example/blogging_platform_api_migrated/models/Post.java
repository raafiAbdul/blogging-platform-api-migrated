package com.example.blogging_platform_api_migrated.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    @Size(min = 3, max = 255)
    private String content;

    @NotNull
    @Size(min = 3, max = 255)
    private String category;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    private String[] tags;

    public Post() {}

    public Post(String title, String content, String category, String[] tags) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String[] getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
