package com.example.blogging_platform_api_migrated.repositories;

import com.example.blogging_platform_api_migrated.BloggingPlatformApiMigratedApplicationTests;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest extends BloggingPlatformApiMigratedApplicationTests {
    @Test
    void testGetPostsByAttributesHappyFlow() {
        postRepository.findByTerm("Common").forEach(System.out::println);
        assertAll(
                () -> assertEquals(3, postRepository.findByTerm("Advice").size()),
                () -> assertEquals(3, postRepository.findByTerm("you").size()),
                () -> assertEquals(2, postRepository.findByTerm("Blog Post").size()),
                () -> assertEquals(1, postRepository.findByTerm("Business").size()),
                () -> assertEquals(2, postRepository.findByTerm("Business").getFirst().getTags().size())
        );
    }
}