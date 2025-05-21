package com.example.raw_and_rare.service.message;
import com.example.raw_and_rare.common.jwt.JwtUtil;
import com.example.raw_and_rare.dto.chat.ChatDto.*;
import com.example.raw_and_rare.dto.user.UserDto.*;
import com.example.raw_and_rare.entity.auth.user.UserStatus;
import com.example.raw_and_rare.entity.message.Message;
import com.example.raw_and_rare.entity.message.MessageType;
import com.example.raw_and_rare.entity.post.Post;
import com.example.raw_and_rare.repository.message.MessageCommandRepository;
import com.example.raw_and_rare.repository.message.MessageQueryRepository;
import com.example.raw_and_rare.repository.post.PostCommandRepository;
import com.example.raw_and_rare.repository.post.PostQueryRepository;
import com.example.raw_and_rare.repository.user.UserCommandRepository;
import com.example.raw_and_rare.repository.user.UserQueryRepository;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import com.example.raw_and_rare.entity.auth.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageCommandRepository messageCommandRepository;
    private final MessageQueryRepository messageQueryRepository;
    private final UserQueryRepository userQueryRepository;

    private final SimpMessagingTemplate messagingTemplate;
    private final PostQueryRepository postQueryRepository;
    private final JwtUtil jwtutil;

    public Message validRequest(String accessToken,Long sender,Long receiver,String content,Long postId) {
        System.out.println(jwtutil.getNicknameFromToken(accessToken));
        User user = userQueryRepository.findUserByNickname(jwtutil.getNicknameFromToken(accessToken)).orElseThrow(()->new RuntimeException("일치하는 유저 정보가 없습니다."));

        if(!user.getId().equals(sender)){
            System.out.println(user.getNickname());
            System.out.println(sender);
            System.out.println(user.getId());
            throw new RuntimeException("유저 정보가 일치하지 않습니다.");
        }
        User target = userQueryRepository.findUserById(receiver).orElseThrow(()->new RuntimeException("일치하는 유저 정보가 없습니다."));
        Post post = postQueryRepository.findById(postId).orElseThrow(()->new RuntimeException("일치하는 게시글이 없습니다."));

        validatePostExists(postId);
        return Message.builder().sender(user).provider(receiver).post(post).content(content).sentAt(Instant.now()).isFiltered(false).filesize(0L).messageType(MessageType.TEXT).build();
    }
    public SendMessageResponse sendMessage(Long postId, SendMessageRequest message,String accessToken,String refreshToken) throws RuntimeException{
        Message msg = validRequest(accessToken,message.senderId(), message.targetId(), message.content(), postId);
        messageCommandRepository.save(msg);

        messagingTemplate.convertAndSend("/chat." + postId.toString(), message);
        return SendMessageResponse.builder()
                .senderId(message.senderId())
                .content(message.content())
                .targetId(message.targetId())
                .postId(postId)
            .build();
    }
    private void validatePostExists(Long postId) {
        Post post = postQueryRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다: " + postId.toString()));
    }

}
