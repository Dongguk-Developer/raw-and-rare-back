package com.example.raw_and_rare.entity.auth;

import com.example.raw_and_rare.entity.auth.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String token;//액세스 토큰

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private Long kakaoId;

}
