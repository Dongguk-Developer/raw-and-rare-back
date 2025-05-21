package com.example.raw_and_rare.repository.post;

import com.example.raw_and_rare.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostQueryRepository extends JpaRepository<Post,Long> {
    Post findById(long id);
}
