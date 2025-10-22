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
    
    // Getters e Setters manuais
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getCardId() { return cardId; }
    public void setCardId(String cardId) { this.cardId = cardId; }
    
    public SrsReview.ReviewResult getResult() { return result; }
    public void setResult(SrsReview.ReviewResult result) { this.result = result; }
}

