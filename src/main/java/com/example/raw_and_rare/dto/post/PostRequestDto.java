package com.example.raw_and_rare.dto.post;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder

public class PostRequestDto {
    private String title;
    private String content;
    private String author;
    private String location;
    private String expiryDate;
    private List<MultipartFile> images; // ✅ 여러 장 받을 수 있도록 수정
}