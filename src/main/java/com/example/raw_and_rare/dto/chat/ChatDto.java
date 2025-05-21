package com.example.raw_and_rare.dto.chat;

import com.example.raw_and_rare.entity.message.MessageType;
import lombok.Builder;

import java.awt.*;

public final class ChatDto {
    @Builder
    public record SendMessageRequest(
            Long senderId,
            Long targetId,
            String content,
            Long postId,
            MessageType messageType
    ){}
    @Builder
    public record SendMessageResponse(
            Long senderId,
            Long targetId,
            String content,
            Long postId
    ){}
    @Builder
    public record JoinChatRoomRequest(
            Long senderId,
            Long targetId,
            String content,
            Long postId
    ){}
    @Builder
    public record JoinChatRoomResponse(
            Long senderId,
            Long targetId,
            String content,
            Long postId
    ){}
}
