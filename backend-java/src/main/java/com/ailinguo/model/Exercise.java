package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "exercises")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String type; // 'multiple_choice', 'fill_blank', 'translation', 'listening'

    @Column(nullable = false)
    private String difficulty; // 'beginner', 'intermediate', 'advanced'

    @Column(nullable = false)
    private String cefrLevel; // 'A1', 'A2', 'B1', 'B2', 'C1', 'C2'

    @Column(nullable = false)
    private Integer xpReward = 10;

    private Integer timeLimit; // in seconds

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExerciseQuestion> questions;

    // Getters adicionais para compatibilidade
    public List<ExerciseQuestion> getQuestions() {
        return questions;
    }

    public Integer getXpReward() {
        return xpReward;
    }

    public String getTitle() {
        return title;
    }
}
