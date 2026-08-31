package com.example.blogging_platform_api_migrated.services;

import com.example.blogging_platform_api_migrated.dtos.PostRequestDto;
import com.example.blogging_platform_api_migrated.dtos.PostResponseDto;
import com.example.blogging_platform_api_migrated.exceptions.NoSuchPostException;
import com.example.blogging_platform_api_migrated.models.Post;
import com.example.blogging_platform_api_migrated.repositories.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class PostService {

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
                post.getContent(),
                post.getCategory(),
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

    @Transactional
    public PostResponseDto updatePost(PostRequestDto postRequestDto, int id) {
        OffsetDateTime now = OffsetDateTime.now();
        Post post = postRepository.findById(id).orElseThrow(() ->
                new NoSuchPostException("No such post with id: " + id));

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setCategory(postRequestDto.getCategory());
        post.setTags(postRequestDto.getTags());
        post.setUpdatedAt(now);
        postRepository.save(post);

        return  mapPostToResponseDto(post);
    }

    @Transactional
    public void deletePost(int id) {
        if(postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new NoSuchPostException("No such post with id: " + id);
        }
    }

    @Transactional
    public PostResponseDto getPostById(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()) {
            return mapPostToResponseDto(optionalPost.get());
        } else {
            throw new NoSuchPostException("No such post with id: " + id);
        }
    }

    @Transactional
    public List<PostResponseDto> getPosts(String term, Integer page, Integer size) {

        page = (page == null || page < 0) ? 0 : page;
        size = (size == null || size <= 0) ? 50 : size;

        List<PostResponseDto> posts = new ArrayList<>();

        if(term == null || term.isEmpty()) {
            postRepository.findAll(PageRequest.of(page, size))
                    .forEach(e -> posts.add(mapPostToResponseDto(e)));

        } else {
            postRepository.findByTerm(term, PageRequest.of(page, size))
                    .forEach(post -> posts.add(mapPostToResponseDto(post)));
        }
        return posts;
    }

}
