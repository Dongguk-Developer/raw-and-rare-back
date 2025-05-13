package com.example.raw_and_rare.repository.user;

import com.example.raw_and_rare.entity.auth.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

//데이터 수정, 삭제, 업데이트 할 때 사용하는 리포지토리
public interface UserCommandRepository extends JpaRepository<User, Long> {
}
