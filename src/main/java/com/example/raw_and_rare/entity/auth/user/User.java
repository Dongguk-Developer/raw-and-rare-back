package com.example.raw_and_rare.entity.auth.user;

import com.example.raw_and_rare.dto.user.UserDto.UserUpdateRequest;
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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;

    @Column(name = "profile_id", nullable = false, unique = true)
    private Long profileId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Instant created_at;

    @Column(nullable = false)
    private Instant updated_at;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    public void updateUser(UserUpdateRequest updateRequestDto) {
        this.nickname = updateRequestDto.nickname();
        this.status = updateRequestDto.status();
        this.updated_at = Instant.now();
    }

    public void updateStatus(UserStatus status) {
        this.status = status;
    }
}
