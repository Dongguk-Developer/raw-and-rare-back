package com.example.raw_and_rare.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String location;
    private String expiryDate;
    private String imagePath;

    private int likeCount;
}
