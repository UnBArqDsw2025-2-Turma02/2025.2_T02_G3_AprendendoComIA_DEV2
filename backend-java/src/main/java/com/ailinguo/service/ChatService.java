package com.ailinguo.service;

import com.ailinguo.model.ChatSession;
import com.ailinguo.model.User;
import com.ailinguo.repository.ChatSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChatService {
    
    private final ChatSessionRepository chatSessionRepository;
    
    public ChatService(ChatSessionRepository chatSessionRepository) {
        this.chatSessionRepository = chatSessionRepository;
    }
    
    public ChatSession createSession(Long userId, User.CefrLevel level, String topic) {
        User user = User.builder().id(userId).build();
        
        ChatSession session = ChatSession.builder()
                .user(user)
                .level(level)
                .topic(topic != null ? topic : "general")
                .summary("")
                .createdAt(LocalDateTime.now())
                .build();
        
        return chatSessionRepository.save(session);
    }
}

