package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.models.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Modifying
    void updatePostById(Post post);
}
