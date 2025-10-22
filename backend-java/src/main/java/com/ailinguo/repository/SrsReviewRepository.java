package com.ailinguo.repository;

import com.ailinguo.model.SrsReview;
import com.ailinguo.model.User;
import com.ailinguo.model.VocabularyCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SrsReviewRepository extends JpaRepository<SrsReview, Long> {
    Optional<SrsReview> findByUserAndVocabularyCard(User user, VocabularyCard vocabularyCard);
    Optional<SrsReview> findByUserIdAndVocabularyCardId(Long userId, Long cardId);
    List<SrsReview> findByUserAndDueAtBefore(User user, LocalDateTime now);
    List<SrsReview> findByUserIdAndDueAtBefore(Long userId, LocalDateTime now);
    
    // Métodos para estatísticas
    long countByUserId(Long userId);
    long countByUserIdAndCreatedAtAfter(Long userId, LocalDateTime date);
    long countByUserIdAndCreatedAtBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    long countByCreatedAtAfter(LocalDateTime date);
}

