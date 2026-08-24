package com.example.blogging_platform_api_migrated.services;

import com.example.blogging_platform_api_migrated.BloggingPlatformApiMigratedApplicationTests;
import com.example.blogging_platform_api_migrated.dtos.PostRequestDto;
import com.example.blogging_platform_api_migrated.dtos.PostResponseDto;
import com.example.blogging_platform_api_migrated.exceptions.NoSuchPostException;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class PostServiceTest extends BloggingPlatformApiMigratedApplicationTests {

    @Autowired
    private PostService postService;

    private static Set<String> tags = new HashSet<>(Arrays.asList("New", "Tag", "New", "Tag"));

    private Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testAddPostHappyFlow() {
        PostRequestDto request = new PostRequestDto(
                "New Title",
                "New Content",
                "New Category",
                tags
        );

        Set<ConstraintViolation<PostRequestDto>> violations = validator.validate(request);

        PostResponseDto response = postService.addPost(request);

        assertAll(
                () -> assertEquals(6, postRepository.count()),
                () -> assertEquals("New Title", response.getTitle()),
                () -> assertEquals("New Content", response.getContent()),
                () -> assertEquals("New Category", response.getCategory()),
                () -> assertEquals(2, response.getTags().size()),
                () -> assertTrue(violations.isEmpty())
        );

    }

    @Test
    void testAddPostErrorFlow() {
        PostRequestDto invalidRequest = new PostRequestDto(
                "c", "a", "r", tags
        );
        Set<ConstraintViolation<PostRequestDto>> constraintViolationExceptions
                = validator.validate(invalidRequest);

        assertFalse(constraintViolationExceptions.isEmpty());
    }

    @Test
    void testUpdatePostHappyFlow() {
        PostRequestDto updatedPost = new PostRequestDto(
                "New Title",
                "New Content",
                "New Category",
                tags
        );
        PostResponseDto response = postService.updatePost(updatedPost, 5);

        assertAll(
                () -> assertEquals(5, postRepository.count()),
                () -> assertEquals("New Title", response.getTitle()),
                () -> assertEquals("New Content", response.getContent()),
                () -> assertEquals("New Category", response.getCategory()),
                () -> assertEquals(2, response.getTags().size()),
                () -> assertEquals(5, response.getId())
        );
    }

    @Test
    void testUpdatePostErrorFlow() {
        PostRequestDto invalidRequest = new PostRequestDto(
                "c", "a", "r", tags
        );
        Set<ConstraintViolation<PostRequestDto>> constraintViolationExceptions
                = validator.validate(invalidRequest);
        assertFalse(constraintViolationExceptions.isEmpty());
    }

    @Test
    void testDeletePostHappyFlow() {
        postService.deletePost(3);
        assertThrows(NoSuchPostException.class, () -> postService.getPostById(3));
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
        List<PostResponseDto> advicePostsNoLimit = postService.getPosts("Advice", 0, null);
        List<PostResponseDto> advicePosts = postService.getPosts("Advice", 0, 10);
        List<PostResponseDto> allPosts = postService.getPosts(null, null, null);
        List<PostResponseDto> emptyPosts = postService.getPosts("", 0, 10);
        List<PostResponseDto> businessPosts = postService.getPosts("Business", 0, 10);
        List<PostResponseDto> firstPage = postService.getPosts(null, 0, 2);
        List<PostResponseDto> secondPage = postService.getPosts(null, 1, 2);

        assertAll(
                () -> assertEquals(3, advicePostsNoLimit.size()),
                () -> assertEquals(5, allPosts.size()),
                () -> assertEquals(5, emptyPosts.size()),
                () -> assertEquals(2, businessPosts.get(0).getTags().size()),
                () -> assertTrue(
                        advicePosts.get(1).getTags().iterator().next().equals("KeepSafe") ||
                                advicePosts.get(1).getTags().iterator().next().equals("Helpful")
                ),
                () -> assertEquals(2, firstPage.size()),
                () -> assertEquals("Inspirational", secondPage.get(0).getCategory())
        );
    }

}