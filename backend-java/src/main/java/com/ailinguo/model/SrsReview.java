package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "srs_reviews")
@EntityListeners(AuditingEntityListener.class)
public class SrsReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private VocabularyCard vocabularyCard;
    
    @Column(name = "due_at")
    private LocalDateTime dueAt;
    
    private Integer interval;
    private Double ease;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "last_result")
    private ReviewResult lastResult;
    
    @Column(name = "reviewed_at")
    private LocalDateTime reviewedAt;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum ReviewResult {
        EASY, GOOD, HARD, AGAIN
    }
    
    // Getters e Setters manuais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public VocabularyCard getVocabularyCard() { return vocabularyCard; }
    public void setVocabularyCard(VocabularyCard vocabularyCard) { this.vocabularyCard = vocabularyCard; }
    
    public LocalDateTime getDueAt() { return dueAt; }
    public void setDueAt(LocalDateTime dueAt) { this.dueAt = dueAt; }
    
    public Integer getInterval() { return interval; }
    public void setInterval(Integer interval) { this.interval = interval; }
    
    public Double getEase() { return ease; }
    public void setEase(Double ease) { this.ease = ease; }
    
    public ReviewResult getLastResult() { return lastResult; }
    public void setLastResult(ReviewResult lastResult) { this.lastResult = lastResult; }
    
    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Método builder manual
    public static SrsReviewBuilder builder() {
        return new SrsReviewBuilder();
    }
    
    public static class SrsReviewBuilder {
        private Long id;
        private User user;
        private VocabularyCard vocabularyCard;
        private LocalDateTime dueAt;
        private Integer interval;
        private Double ease;
        private ReviewResult lastResult;
        private LocalDateTime reviewedAt;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public SrsReviewBuilder id(Long id) { this.id = id; return this; }
        public SrsReviewBuilder user(User user) { this.user = user; return this; }
        public SrsReviewBuilder vocabularyCard(VocabularyCard vocabularyCard) { this.vocabularyCard = vocabularyCard; return this; }
        public SrsReviewBuilder dueAt(LocalDateTime dueAt) { this.dueAt = dueAt; return this; }
        public SrsReviewBuilder interval(Integer interval) { this.interval = interval; return this; }
        public SrsReviewBuilder ease(Double ease) { this.ease = ease; return this; }
        public SrsReviewBuilder lastResult(ReviewResult lastResult) { this.lastResult = lastResult; return this; }
        public SrsReviewBuilder reviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; return this; }
        public SrsReviewBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public SrsReviewBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        
        public SrsReview build() {
            SrsReview review = new SrsReview();
            review.setId(id);
            review.setUser(user);
            review.setVocabularyCard(vocabularyCard);
            review.setDueAt(dueAt);
            review.setInterval(interval);
            review.setEase(ease);
            review.setLastResult(lastResult);
            review.setReviewedAt(reviewedAt);
            review.setCreatedAt(createdAt);
            review.setUpdatedAt(updatedAt);
            return review;
        }
    }
}

