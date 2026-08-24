package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.BloggingPlatformApiMigratedApplicationTests;
import com.example.blogging_platform_api_migrated.models.Post;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest extends BloggingPlatformApiMigratedApplicationTests {
    @Test
    void testGetPostsByAttributesHappyFlow() {
        List<Post> adviceResults = postRepository.findByTerm("Advice");
        List<Post> youResults = postRepository.findByTerm("you");
        List<Post> blogPostResults = postRepository.findByTerm("Blog Post");
        List<Post> businessResults = postRepository.findByTerm("Business");
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