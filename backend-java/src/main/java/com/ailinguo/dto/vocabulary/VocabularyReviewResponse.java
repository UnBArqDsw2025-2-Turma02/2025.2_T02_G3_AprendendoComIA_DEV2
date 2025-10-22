package com.ailinguo.dto.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyReviewResponse {
    private Boolean success;
    private LocalDateTime nextDue;
    
    // Getters e Setters manuais
    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }
    
    public LocalDateTime getNextDue() { return nextDue; }
    public void setNextDue(LocalDateTime nextDue) { this.nextDue = nextDue; }
    
    // Método builder manual
    public static VocabularyReviewResponseBuilder builder() {
        return new VocabularyReviewResponseBuilder();
    }
    
    public static class VocabularyReviewResponseBuilder {
        private Boolean success;
        private LocalDateTime nextDue;
        
        public VocabularyReviewResponseBuilder success(Boolean success) { this.success = success; return this; }
        public VocabularyReviewResponseBuilder nextDue(LocalDateTime nextDue) { this.nextDue = nextDue; return this; }
        
        public VocabularyReviewResponse build() {
            VocabularyReviewResponse response = new VocabularyReviewResponse();
            response.setSuccess(success);
            response.setNextDue(nextDue);
            return response;
        }
    }
}

