package com.ailinguo.service;

import com.ailinguo.dto.vocabulary.VocabularyReviewRequest;
import com.ailinguo.dto.vocabulary.VocabularyReviewResponse;
import com.ailinguo.model.SrsReview;
import com.ailinguo.model.User;
import com.ailinguo.model.VocabularyCard;
import com.ailinguo.repository.SrsReviewRepository;
import com.ailinguo.repository.VocabularyCardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class VocabularyService {
    
    private final VocabularyCardRepository vocabularyCardRepository;
    private final SrsReviewRepository srsReviewRepository;
    
    public VocabularyService(VocabularyCardRepository vocabularyCardRepository, 
                            SrsReviewRepository srsReviewRepository) {
        this.vocabularyCardRepository = vocabularyCardRepository;
        this.srsReviewRepository = srsReviewRepository;
    }
    
    public List<VocabularyCard> getDueCards(String userId, int limit) {
        // For now, return mock cards
        // In production, you'd filter by actual due dates from srsReviews
        return Arrays.asList(
                VocabularyCard.builder()
                        .id("card1")
                        .term("apple")
                        .meaning("maçã")
                        .example("I eat an apple every day")
                        .cefrLevel(User.CefrLevel.A1)
                        .build(),
                VocabularyCard.builder()
                        .id("card2")
                        .term("beautiful")
                        .meaning("bonito/bonita")
                        .example("The sunset is beautiful")
                        .cefrLevel(User.CefrLevel.A2)
                        .build(),
                VocabularyCard.builder()
                        .id("card3")
                        .term("necessary")
                        .meaning("necessário")
                        .example("It is necessary to study English")
                        .cefrLevel(User.CefrLevel.B1)
                        .build()
        ).subList(0, Math.min(limit, 3));
    }
    
    public VocabularyReviewResponse reviewCard(VocabularyReviewRequest request) {
        // Calculate next interval based on SRS algorithm
        int interval;
        double ease;
        
        switch (request.getResult()) {
            case AGAIN:
                interval = 1;
                ease = 1.3;
                break;
            case HARD:
                interval = 1;
                ease = 1.3;
                break;
            case GOOD:
                interval = 4;
                ease = 2.0;
                break;
            case EASY:
                interval = 7;
                ease = 2.5;
                break;
            default:
                interval = 1;
                ease = 1.3;
        }
        
        LocalDateTime nextDue = LocalDateTime.now().plusDays(interval);
        
        // Save or update review
        SrsReview review = srsReviewRepository
                .findByUserIdAndCardId(request.getUserId(), request.getCardId())
                .orElse(new SrsReview());
        
        review.setUserId(request.getUserId());
        review.setCardId(request.getCardId());
        review.setDueAt(nextDue);
        review.setInterval(interval);
        review.setEase(ease);
        review.setLastResult(request.getResult());
        review.setReviewedAt(LocalDateTime.now());
        
        srsReviewRepository.save(review);
        
        return VocabularyReviewResponse.builder()
                .success(true)
                .nextDue(nextDue)
                .build();
    }
}

