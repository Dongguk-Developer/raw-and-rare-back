package com.example.raw_and_rare.repository.message;


import com.example.raw_and_rare.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageCommandRepository extends JpaRepository<Message, Long> {
}
