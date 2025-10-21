package com.ailinguo.service;

import com.ailinguo.model.ChatSession;
import com.ailinguo.model.User;
import com.ailinguo.repository.ChatSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatService {
    
    private final ChatSessionRepository chatSessionRepository;
    
    public ChatService(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = chatSessionRepository;
    }
    
    public ChatSession createSession(String userId, User.CefrLevel level, String topic) {
        ChatSession session = ChatSession.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .level(level)
                .topic(topic != null ? topic : "general")
                .summary("")
                .createdAt(LocalDateTime.now())
                .build();
        
        return chatSessionRepository.save(session);
    }
}

