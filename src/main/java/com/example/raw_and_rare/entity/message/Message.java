package com.example.raw_and_rare.entity.message;

import com.example.raw_and_rare.entity.auth.user.Users;
import com.example.raw_and_rare.entity.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users sender;

    @JoinColumn(name = "user_id",nullable = false)
    private Long provider;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Instant sentAt;

    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean isFiltered;
}
