package com.ailinguo.repository;

import com.ailinguo.model.ChatSession;
import com.ailinguo.model.ChatTurn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatTurnRepository extends JpaRepository<ChatTurn, Long> {
    List<ChatTurn> findByChatSessionOrderByCreatedAtAsc(ChatSession chatSession);
    List<ChatTurn> findByChatSessionIdOrderByCreatedAtAsc(Long sessionId);
    
    // Métodos para estatísticas
    long countByUserId(Long userId);
    long countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);
    long countByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    long countByCreatedAtAfter(LocalDateTime date);
}

