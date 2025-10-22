package com.ailinguo.repository;

import com.ailinguo.model.ChatSession;
import com.ailinguo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    List<ChatSession> findByUserOrderByCreatedAtDesc(User user);
    List<ChatSession> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    // Métodos para estatísticas
    long countByUserId(Long userId);
}

