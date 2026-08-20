package com.example.blogging_platform_api_migrated.services;

import com.example.blogging_platform_api_migrated.dtos.PostRequestDto;
import com.example.blogging_platform_api_migrated.dtos.PostResponseDto;
import com.example.blogging_platform_api_migrated.models.Post;
import com.example.blogging_platform_api_migrated.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    public  PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    private Post mapRequestDtoToPost(
            PostRequestDto postRequestDto,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt) {
        return new Post(
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                postRequestDto.getCategory(),
                postRequestDto.getTags(),
                createdAt, updatedAt
        );
    }

    private PostResponseDto mapPostToResponseDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent(),
                post.getTags(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional
    public PostResponseDto addPost(PostRequestDto postRequestDto) {
        OffsetDateTime now = OffsetDateTime.now();
        Post post = mapRequestDtoToPost(postRequestDto, now, now);
        postRepository.save(post);
        return mapPostToResponseDto(post);
    }



}
