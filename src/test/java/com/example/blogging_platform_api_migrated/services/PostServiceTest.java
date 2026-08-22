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
                () -> assertEquals(2, response.getTags().size())
        );

    }

    @Test
    void testAddPostErrorFlow() {
        assertThrows(ConstraintViolationException.class, () -> {
            PostRequestDto invalidRequest = new PostRequestDto(
                    "c", "a", "r", tags
            );
            postService.addPost(invalidRequest);
        });
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


        assertThrows(TransactionSystemException.class, () -> {
            PostRequestDto invalidRequest = new PostRequestDto(
                    "c", "a", "r", tags
            );
            postService.updatePost(invalidRequest, 1);
        });
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
        postService.getPosts("Advice", 0, null).forEach(prdto -> System.out.println(prdto.toString()));
        assertAll(
                () -> assertEquals(3, postService.getPosts("Advice", 0, null).size()),
                () -> assertEquals(5, postService.getPosts(null, null, null).size()),
                () -> assertEquals(5, postService.getPosts("", 0, 10).size()),
                () -> assertEquals(1, postService.getPosts("Business", 0, 10)
                        .getFirst().getTags().size()),
                () -> assertTrue(
                        postService.getPosts("Advice", 0, 10)
                                .get(1).getTags().iterator().next().equals("KeepSafe") ||
                                postService.getPosts("Advice", 0, 10).get(1)
                                        .getTags().iterator().next().equals("Helpful")
                ),
                () -> assertEquals(2, postService.getPosts(null, 0, 2).size()),
                () -> assertEquals("Inspirational", postService.getPosts(null, 1, 2)
                        .getFirst().getCategory())
        );
    }

}