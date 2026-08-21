package com.example.blogging_platform_api_migrated.services;

import com.example.blogging_platform_api_migrated.BloggingPlatformApiMigratedApplicationTests;
import com.example.blogging_platform_api_migrated.dtos.PostRequestDto;
import com.example.blogging_platform_api_migrated.dtos.PostResponseDto;
import com.example.blogging_platform_api_migrated.exceptions.NoSuchPostException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PostServiceTest extends BloggingPlatformApiMigratedApplicationTests {

    @Autowired
    private PostService postService;

    private static Set<String> tags = new HashSet<>(Arrays.asList("New", "Tag", "New", "Tag"));

    @Test
    void testAddPostHappyFlow() {
        PostRequestDto request = new PostRequestDto(
                "New Title",
                "New Content",
                "New Category",
                tags
        );

        PostResponseDto response = postService.addPost(request);

        assertAll(
                () -> assertEquals(5, postRepository.count()),
                () -> assertEquals("New Title", response.getTitle()),
                () -> assertEquals("New Content", response.getContent()),
                () -> assertEquals("New Category", response.getCategory()),
                () -> assertEquals(4, response.getTags().size())
        );

    }

    @Test
    void testAddPostErrorFlow() {
        PostRequestDto invalidRequest = new PostRequestDto(
                "c", "a", "r", tags
        );

        assertThrows(ConstraintViolationException.class, () -> postService.addPost(invalidRequest));
    }

    @Test
    void testUpdatePostHappyFlow() {
        PostRequestDto newRequest = new PostRequestDto(
                "New Title",
                "New Content",
                "New Category",
                tags
        );
        PostResponseDto response = postService.updatePost(newRequest, 5);

        assertAll(
                () -> assertEquals(5, postRepository.count()),
                () -> assertEquals("New Title", response.getTitle()),
                () -> assertEquals("New Content", response.getContent()),
                () -> assertEquals("New Category", response.getCategory()),
                () -> assertEquals(4, response.getTags().size()),
                () -> assertEquals(5, response.getId())
        );
    }

    @Test
    void testUpdatePostErrorFlow() {
        PostRequestDto invalidRequest = new PostRequestDto(
                "c", "a", "r", tags
        );

        assertThrows(TransactionSystemException.class, () -> postService.updatePost(invalidRequest, 1));
    }

    @Test
    void testDeletePostHappyFlow() {
        assertTrue(postService.deletePost(3));
    }

    @Test
    void testDeletePostErrorFlow() {
        assertThrows(NoSuchPostException.class, () -> postService.deletePost(100));
    }

    @Test
    void testGetPostByIdHappyFlow() {
        assertEquals("My Second Blog Post", postService.getPostById(2).getTitle());
    }

    @Test
    void testGetPostByIdErrorFlow() {
        assertThrows(NoSuchPostException.class, () -> postService.getPostById(100));
    }

    @Test
    void testGetAllPostsHappyFlow() {
        assertAll(
                () -> assertEquals(3, postService.getPosts("Advice").size()),
                () -> assertEquals(5, postService.getPosts(null).size()),
                () -> assertEquals(5, postService.getPosts("").size()),
                () -> assertEquals(2, postService.getPosts("Business")
                        .getFirst().getTags().size())
        );
    }

}