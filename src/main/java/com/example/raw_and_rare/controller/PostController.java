package com.example.raw_and_rare.controller;

import com.example.raw_and_rare.entity.post.Post;
import com.example.raw_and_rare.dto.post.PostRequestDto;
import com.example.raw_and_rare.dto.post.PostResponseDto;
import com.example.raw_and_rare.repository.post.PostRepository;
import com.example.raw_and_rare.service.post.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    // 생성
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<PostResponseDto> createPost(@ModelAttribute PostRequestDto dto) {
        Post savedPost = postService.savePost(dto);

        PostResponseDto response = new PostResponseDto(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getAuthor(),
                savedPost.getLocation(),
                savedPost.getExpiryDate(),
                savedPost.getImagePath(),
                savedPost.getLikeCount()
        );

        return ResponseEntity.ok(response);
    }

    // 모든 조회
    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> new PostResponseDto(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor(),
                        post.getLocation(),
                        post.getExpiryDate(),
                        post.getImagePath(),
                        post.getLikeCount()
                ))
                .collect(Collectors.toList());
    }

    // ID 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        PostResponseDto response = new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getLocation(),
                post.getExpiryDate(),
                post.getImagePath(),
                post.getLikeCount()
        );

        return ResponseEntity.ok(response);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<PostResponseDto> likePost(@PathVariable Long id) {
        Post likedPost = postService.likePost(id);

        PostResponseDto response = new PostResponseDto(
                likedPost.getId(),
                likedPost.getTitle(),
                likedPost.getContent(),
                likedPost.getAuthor(),
                likedPost.getLocation(),
                likedPost.getExpiryDate(),
                likedPost.getImagePath(),
                likedPost.getLikeCount()
        );

        return ResponseEntity.ok(response);
    }

    // 수정
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @ModelAttribute PostRequestDto dto,
            @RequestParam(value = "deletedImages", required = false) String deletedImagesJson) {

        List<String> deletedImages = new ArrayList<>();
        if (deletedImagesJson != null && !deletedImagesJson.isEmpty()) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                deletedImages = mapper.readValue(deletedImagesJson, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        Post updatedPost = postService.updatePost(id, dto, deletedImages);

        PostResponseDto response = new PostResponseDto(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getAuthor(),
                updatedPost.getLocation(),
                updatedPost.getExpiryDate(),
                updatedPost.getImagePath(),
                updatedPost.getLikeCount()
        );

        return ResponseEntity.ok(response);
    }
}
