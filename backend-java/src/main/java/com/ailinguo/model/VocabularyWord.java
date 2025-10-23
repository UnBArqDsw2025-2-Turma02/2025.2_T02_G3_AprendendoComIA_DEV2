package com.ailinguo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vocabulary_words")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private VocabularyCategory category;

    @Column(nullable = false)
    private String englishWord;

    @Column(nullable = false)
    private String portugueseTranslation;

    private String phonetic;

    @Column(columnDefinition = "TEXT")
    private String definition;

    @Column(columnDefinition = "TEXT")
    private String exampleSentence;

    @Column(nullable = false)
    private String difficulty; // 'beginner', 'intermediate', 'advanced'

    @Column(nullable = false)
    private String cefrLevel; // 'A1', 'A2', 'B1', 'B2', 'C1', 'C2'

    @Column(nullable = false)
    @Builder.Default
    private Integer xpReward = 5;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters adicionais para compatibilidade
    public String getEnglishWord() {
        return englishWord;
    }

    public Integer getXpReward() {
        return xpReward;
    }

    public Long getId() {
        return id;
    }

    public String getPortugueseTranslation() {
        return portugueseTranslation;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCefrLevel() {
        return cefrLevel;
    }
}
