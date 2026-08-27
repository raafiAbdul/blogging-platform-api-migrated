package com.example.blogging_platform_api_migrated.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Post")
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String title;
    private String content;
    private String category;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    // declares that this is a collection of primitive Java types
    // eager fetch type fetches the whole collection when parent is called
    @ElementCollection(fetch = FetchType.EAGER)
    // creates another table called post_tags that has a column named post_id
    // containing the foreign key mapped to the @Id annotated attribute of the
    // parent class
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    // names the column that stores the actual collection of text tags
    @Column(name = "tags")
    private Set<String> tags;

    public Post() {}

    public Post(
            String title, String content,
            String category, Set<String> tags,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getTags() {
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

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        String tagsString = (tags == null) ? null : tags.toString();
        if(createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
        return "{id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", tags=" + "[" + tagsString + "]" +
                ", created_at=" + createdAt +
                ", updated_at=" + updatedAt;
    }
}
