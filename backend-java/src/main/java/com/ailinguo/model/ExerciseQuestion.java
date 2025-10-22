package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "exercise_questions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Column(nullable = false)
    private String questionType; // 'multiple_choice', 'fill_blank', 'translation'

    @Column(nullable = false, columnDefinition = "TEXT")
    private String correctAnswer;

    @Column(columnDefinition = "JSONB")
    private String options; // JSON array for multiple choice questions

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(nullable = false)
    private Integer orderIndex;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Getters adicionais para compatibilidade
    public Long getId() {
        return id;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }
}
