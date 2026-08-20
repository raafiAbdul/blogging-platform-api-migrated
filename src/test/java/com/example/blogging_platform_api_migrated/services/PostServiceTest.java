package com.example.blogging_platform_api_migrated.services;

import com.example.blogging_platform_api_migrated.BloggingPlatformApiMigratedApplicationTests;
import com.example.blogging_platform_api_migrated.dtos.PostRequestDto;
import com.example.blogging_platform_api_migrated.dtos.PostResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;


import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest extends BloggingPlatformApiMigratedApplicationTests {

    @Autowired
    private PostService postService;

    @Test
    void testAddPostHappyFlow() {
        PostRequestDto request = new PostRequestDto(
                "New Title",
                "New Content",
                "New Category",
                new String[]{"New", "Tags"}
        );

        PostResponseDto response = postService.addPost(request);

        assertAll(
                () -> assertEquals(5, postRepository.count()),
                () -> assertEquals("New Title", response.getTitle()),
                () -> assertEquals("New Content", response.getContent()),
                () -> assertEquals("New Category", response.getCategory()),
                () -> assertArrayEquals(new String[]{"New", "Tags"}, response.getTags()),
                () -> assertNotNull(response.getId())
        );

    }

    @Test
    void testAddPostErrorFlow() {
        PostRequestDto invalidRequest = new PostRequestDto(
                "h", "e", "l", null
        );

        assertThrows(ConstraintViolationException.class, () -> {
            postService.addPost(invalidRequest);
        });
    }

    @Test
    void testUpdatePostHappyFlow() {
        PostRequestDto oldRequest = new PostRequestDto(
                "Old Title",
                "Old Content",
                "Old Category",
                new String[]{"Old", "Tags"}
        );
        PostRequestDto newRequest = new PostRequestDto(
                "New Title",
                "New Content",
                "New Category",
                new String[]{"New", "Tags"}
        );
        postService.addPost(oldRequest);
        PostResponseDto response = postService.updatePost(newRequest, 5);

        assertAll(
                () -> assertEquals(5, postRepository.count()),
                () -> assertEquals("New Title", response.getTitle()),
                () -> assertEquals("New Content", response.getContent()),
                () -> assertEquals("New Category", response.getCategory()),
                () -> assertArrayEquals(new String[]{"New", "Tags"}, response.getTags()),
                () -> assertEquals(5, response.getId())
        );
    }

    @Test
    void testUpdatePostErrorFlow() {
        PostRequestDto invalidRequest = new PostRequestDto(
                "h", "e", "l", null
        );

        assertThrows(TransactionSystemException.class, () -> {
            postService.updatePost(invalidRequest, 1);
        });
    }


}