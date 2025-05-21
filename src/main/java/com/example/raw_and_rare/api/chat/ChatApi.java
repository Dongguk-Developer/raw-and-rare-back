package com.example.raw_and_rare.api.chat;


import com.example.raw_and_rare.dto.chat.ChatDto.*;
import com.example.raw_and_rare.service.message.MessageService;
import com.example.raw_and_rare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

@Controller
@RequiredArgsConstructor
public class ChatApi {
    private final MessageService messageService;
    @MessageMapping("/chat/sendMessage/{postId}")
    @SendTo("/chatroom/{postId}")
    public SendMessageResponse sendMessage(@DestinationVariable Long postId, @Payload SendMessageRequest message,SimpMessageHeaderAccessor headerAccessor
    ) {
        String accessToken = headerAccessor.getFirstNativeHeader("accessToken");
        String refreshToken = headerAccessor.getFirstNativeHeader("refreshToken");
        System.out.println(postId+", "+message+", "+accessToken+", "+refreshToken);
        return messageService.sendMessage(postId, message, accessToken, refreshToken);
    }
}
