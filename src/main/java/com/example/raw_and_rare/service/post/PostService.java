package com.example.raw_and_rare.service.post;

import com.example.raw_and_rare.entity.post.Post;
import com.example.raw_and_rare.dto.post.PostRequestDto;
import com.example.raw_and_rare.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 새 게시물 저장
    public Post savePost(PostRequestDto dto) {
        List<String> storedFileNames = new ArrayList<>();

        List<MultipartFile> images = dto.getImages();
        if (images != null && !images.isEmpty()) {
            String uploadDir = "C:/upload/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String originalFilename = image.getOriginalFilename();
                    String storedFileName = UUID.randomUUID() + "_" + originalFilename;
                    File dest = new File(uploadDir + storedFileName);

                    try {
                        image.transferTo(dest);
                        storedFileNames.add(storedFileName);
                        System.out.println("✅ 이미지 저장 성공: " + storedFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        String imagePath = String.join(",", storedFileNames);

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .imagePath(imagePath)
                .location(dto.getLocation())
                .expiryDate(dto.getExpiryDate())
                .build();

        System.out.println("✅ 저장할 Post 객체: " + post);
        return postRepository.save(post);
    }

    // 좋아요
    public Post likePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        post = Post.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .imagePath(post.getImagePath())
                .likeCount(post.getLikeCount() + 1)
                .location(post.getLocation())
                .expiryDate(post.getExpiryDate())
                .build();

        return postRepository.save(post);
    }

    // 수정
    public Post updatePost(Long id, PostRequestDto dto, List<String> deletedImages) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));

        List<String> existing = new ArrayList<>();
        if (post.getImagePath() != null && !post.getImagePath().isBlank()) {
            existing = new ArrayList<>(List.of(post.getImagePath().split(",")));
        }

        // ✅ 삭제 요청된 이미지 제거
        if (deletedImages != null && !deletedImages.isEmpty()) {
            existing.removeAll(deletedImages);
        }

        // 신규 이미지 저장
        List<String> storedFileNames = new ArrayList<>();
        List<MultipartFile> images = dto.getImages();
        if (images != null && !images.isEmpty()) {
            String uploadDir = "C:/upload/";

            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    String originalFilename = image.getOriginalFilename();
                    String storedFileName = UUID.randomUUID() + "_" + originalFilename;
                    File dest = new File(uploadDir + storedFileName);

                    try {
                        image.transferTo(dest);
                        storedFileNames.add(storedFileName);
                        System.out.println("✅ 수정 - 이미지 저장 성공: " + storedFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        List<String> allImages = new ArrayList<>(existing);
        allImages.addAll(storedFileNames);
        String finalImagePath = String.join(",", allImages);

        Post updatedPost = Post.builder()
                .id(post.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .location(dto.getLocation())
                .expiryDate(dto.getExpiryDate())
                .imagePath(finalImagePath)
                .likeCount(post.getLikeCount()) // 좋아요 수 유지
                .build();

        return postRepository.save(updatedPost);
    }
}