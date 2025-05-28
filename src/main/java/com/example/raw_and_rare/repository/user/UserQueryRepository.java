package com.example.raw_and_rare.repository.user;

import com.example.raw_and_rare.entity.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserQueryRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);

    Optional<User> findUserByProfileId(Long profile_id);
}
