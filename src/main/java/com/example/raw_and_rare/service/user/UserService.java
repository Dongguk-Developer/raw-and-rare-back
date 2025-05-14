package com.example.raw_and_rare.service.user;

import com.example.raw_and_rare.dto.user.UserDto.*;
import com.example.raw_and_rare.entity.auth.user.UserStatus;
import com.example.raw_and_rare.repository.user.UserCommandRepository;
import com.example.raw_and_rare.repository.user.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.raw_and_rare.entity.auth.user.User;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    @Transactional
    public UserUpdateResponse updateUser(Long userId,UserUpdateRequest request) {
        User user = userQueryRepository.findById(userId).orElseThrow(()->new RuntimeException("해당 유저가 존재하지 않습니다."));
        user.updateUser(request);
        userCommandRepository.save(user);
        return UserUpdateResponse.builder().message("업데이트 성공").build();
    }
}
