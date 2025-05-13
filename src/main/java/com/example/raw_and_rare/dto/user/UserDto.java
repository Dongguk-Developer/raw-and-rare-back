package com.example.raw_and_rare.dto.user;

import com.example.raw_and_rare.entity.auth.user.Profile;
import com.example.raw_and_rare.entity.auth.user.UserStatus;
import jakarta.persistence.*;
import lombok.Builder;

public final class UserDto {
    @Builder
    public record UserUpdateRequest(
            String username,
            String nickname,
            String password,
            UserStatus status
    ){}
    @Builder
    public record UserUpdateResponse(
            String message
    ){}
}
