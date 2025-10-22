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
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vocabulary_cards")
@EntityListeners(AuditingEntityListener.class)
public class VocabularyCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String term;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String meaning;
    
    @Column(columnDefinition = "TEXT")
    private String example;
    
    @Enumerated(EnumType.STRING)
    private User.CefrLevel cefrLevel;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relacionamentos
    @OneToMany(mappedBy = "vocabularyCard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SrsReview> srsReviews;
    
    // Getters e Setters manuais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTerm() { return term; }
    public void setTerm(String term) { this.term = term; }
    
    public String getMeaning() { return meaning; }
    public void setMeaning(String meaning) { this.meaning = meaning; }
    
    public String getExample() { return example; }
    public void setExample(String example) { this.example = example; }
    
    public User.CefrLevel getCefrLevel() { return cefrLevel; }
    public void setCefrLevel(User.CefrLevel cefrLevel) { this.cefrLevel = cefrLevel; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<SrsReview> getSrsReviews() { return srsReviews; }
    public void setSrsReviews(List<SrsReview> srsReviews) { this.srsReviews = srsReviews; }
    
    // Método builder manual
    public static VocabularyCardBuilder builder() {
        return new VocabularyCardBuilder();
    }
    
    public static class VocabularyCardBuilder {
        private Long id;
        private String term;
        private String meaning;
        private String example;
        private User.CefrLevel cefrLevel;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<SrsReview> srsReviews;
        
        public VocabularyCardBuilder id(Long id) { this.id = id; return this; }
        public VocabularyCardBuilder term(String term) { this.term = term; return this; }
        public VocabularyCardBuilder meaning(String meaning) { this.meaning = meaning; return this; }
        public VocabularyCardBuilder example(String example) { this.example = example; return this; }
        public VocabularyCardBuilder cefrLevel(User.CefrLevel cefrLevel) { this.cefrLevel = cefrLevel; return this; }
        public VocabularyCardBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public VocabularyCardBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public VocabularyCardBuilder srsReviews(List<SrsReview> srsReviews) { this.srsReviews = srsReviews; return this; }
        
        public VocabularyCard build() {
            VocabularyCard card = new VocabularyCard();
            card.setId(id);
            card.setTerm(term);
            card.setMeaning(meaning);
            card.setExample(example);
            card.setCefrLevel(cefrLevel);
            card.setCreatedAt(createdAt);
            card.setUpdatedAt(updatedAt);
            card.setSrsReviews(srsReviews);
            return card;
        }
    }
}

