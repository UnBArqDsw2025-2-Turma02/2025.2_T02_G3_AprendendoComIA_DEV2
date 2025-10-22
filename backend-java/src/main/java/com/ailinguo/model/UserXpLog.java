package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_xp_log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserXpLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer xpChange; // can be positive or negative

    @Column(nullable = false)
    private String sourceType; // 'exercise', 'vocabulary', 'achievement', 'bonus'

    private Long sourceId; // reference to exercise, vocabulary word, etc.

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Setters adicionais para compatibilidade
    public void setXpChange(Integer xpChange) {
        this.xpChange = xpChange;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
