package com.ailinguo.controller;

import com.ailinguo.model.ChatSession;
import com.ailinguo.model.User;
import com.ailinguo.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    
    @PostMapping("/sessions")
    public ResponseEntity<?> createSession(
            @RequestBody Map<String, Object> request,
            Authentication authentication
    ) {
        try {
            String userId = (String) request.get("userId");
            String authUserId = (String) authentication.getPrincipal();
            
            if (!authUserId.equals(userId)) {
                return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
            }
            
            User.CefrLevel level = User.CefrLevel.valueOf((String) request.get("level"));
            String topic = (String) request.getOrDefault("topic", "general");
            
            ChatSession session = chatService.createSession(userId, level, topic);
            return ResponseEntity.ok(session);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }
}

