package com.example.raw_and_rare.repository.post;

import com.example.raw_and_rare.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
