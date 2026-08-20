package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Modifying
    @Query(value = "update Post p" +
            "set title = :title," +
            "set content = :content," +
            "set category = :category," +
            "set tags = :tags," +
            "set updated_at = :updated_at", nativeQuery = true)
    void updatePostById(@Param("title") String title,
                        @Param("content") String content,
                        @Param("category") String category,
                        @Param("tags") String[] tags,
                        @Param("updated_at")OffsetDateTime updatedAt);
}
