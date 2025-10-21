package com.ailinguo.repository;

import com.ailinguo.model.SrsReview;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SrsReviewRepository extends MongoRepository<SrsReview, String> {
    Optional<SrsReview> findByUserIdAndCardId(String userId, String cardId);
    List<SrsReview> findByUserIdAndDueAtBefore(String userId, LocalDateTime now);
}

