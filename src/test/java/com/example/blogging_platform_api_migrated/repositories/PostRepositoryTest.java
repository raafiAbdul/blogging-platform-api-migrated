package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.BloggingPlatformApiMigratedApplicationTests;
import com.example.blogging_platform_api_migrated.models.Post;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest extends BloggingPlatformApiMigratedApplicationTests {
    @Test
    void testGetPostsByAttributesHappyFlow() {
        List<Post> adviceResults = postRepository.findByTerm("Advice", PageRequest.of(0, 10));
        List<Post> youResults = postRepository.findByTerm("you", PageRequest.of(0, 10));
        List<Post> blogPostResults = postRepository.findByTerm("Blog Post", PageRequest.of(0, 10));
        List<Post> businessResults = postRepository.findByTerm("Business", PageRequest.of(0, 10));
        List<String> businessTags = List.copyOf(businessResults.get(0).getTags());

        assertAll(
                () -> assertEquals(3, adviceResults.size()),
                () -> assertEquals(3, youResults.size()),
                () -> assertEquals(2, blogPostResults.size()),
                () -> assertEquals(1, businessResults.size()),
                () -> assertTrue((businessTags.get(0).equals("JobHunting") ||
                        businessTags.get(0).equals("Business")))
        );
    }
}