package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_vocabulary_progress", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "vocabulary_word_id"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVocabularyProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_word_id", nullable = false)
    private VocabularyWord vocabularyWord;

    @Column(nullable = false)
    private Integer masteryLevel = 0; // 0-5 scale

    @Column(nullable = false)
    private Integer timesStudied = 0;

    private LocalDateTime lastStudied;

    @Column(nullable = false)
    private Integer xpEarned = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Getters adicionais para compatibilidade
    public Integer getTimesStudied() {
        return timesStudied;
    }

    public void setLastStudied(LocalDateTime lastStudied) {
        this.lastStudied = lastStudied;
    }

    public Integer getMasteryLevel() {
        return masteryLevel;
    }

    public Integer getXpEarned() {
        return xpEarned;
    }

    // Setters adicionais para compatibilidade
    public void setTimesStudied(Integer timesStudied) {
        this.timesStudied = timesStudied;
    }

    public void setMasteryLevel(Integer masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public void setXpEarned(Integer xpEarned) {
        this.xpEarned = xpEarned;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVocabularyWord(VocabularyWord vocabularyWord) {
        this.vocabularyWord = vocabularyWord;
    }
}
