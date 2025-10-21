package com.ailinguo.dto.vocabulary;

import com.ailinguo.model.SrsReview;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VocabularyReviewRequest {
    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotBlank(message = "Card ID is required")
    private String cardId;
    
    @NotNull(message = "Result is required")
    private SrsReview.ReviewResult result;
}

