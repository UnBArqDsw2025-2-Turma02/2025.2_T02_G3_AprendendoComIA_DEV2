package com.ailinguo.repository;

import com.ailinguo.model.User;
import com.ailinguo.model.VocabularyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocabularyCardRepository extends JpaRepository<VocabularyCard, Long> {
    List<VocabularyCard> findByCefrLevel(User.CefrLevel level);
    
    // Métodos para estatísticas
    long countByCefrLevel(User.CefrLevel cefrLevel);
}

