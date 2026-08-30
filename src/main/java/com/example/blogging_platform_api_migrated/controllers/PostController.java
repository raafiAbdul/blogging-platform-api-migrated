package com.example.blogging_platform_api_migrated.controllers;

import com.example.blogging_platform_api_migrated.dtos.PostRequestDto;
import com.example.blogging_platform_api_migrated.dtos.PostResponseDto;
import com.example.blogging_platform_api_migrated.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDto> addPost(@Valid @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.addPost(postRequestDto));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable int id, @Valid @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity
                .ok().body(postService.updatePost(postRequestDto, id));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable int id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(
            @RequestParam(required = false) String term,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return ResponseEntity.ok().body(postService.getPosts(term, page, size));
    }

}
