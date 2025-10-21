package com.ailinguo.repository;

import com.ailinguo.model.ChatTurn;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatTurnRepository extends MongoRepository<ChatTurn, String> {
    List<ChatTurn> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}

