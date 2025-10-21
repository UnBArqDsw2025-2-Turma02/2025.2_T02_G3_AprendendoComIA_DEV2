package com.ailinguo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "srsReviews")
public class SrsReview {
    private String userId;
    private String cardId;
    private LocalDateTime dueAt;
    private Integer interval;
    private Double ease;
    private ReviewResult lastResult;
    private LocalDateTime reviewedAt;
    
    public enum ReviewResult {
        EASY, GOOD, HARD, AGAIN
    }
}

