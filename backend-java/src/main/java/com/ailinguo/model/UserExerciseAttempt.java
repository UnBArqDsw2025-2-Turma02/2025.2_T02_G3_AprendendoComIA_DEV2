package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_exercise_attempts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserExerciseAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal score; // percentage score

    @Column(nullable = false)
    private Integer xpEarned = 0;

    private Integer timeSpent; // in seconds

    private LocalDateTime completedAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Setters adicionais para compatibilidade
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public void setXpEarned(Integer xpEarned) {
        this.xpEarned = xpEarned;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
}
