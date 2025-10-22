package com.ailinguo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_attempts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TaskAttempt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    
    @Column(nullable = false)
    private Integer selectedAnswerIndex;
    
    @Column(nullable = false)
    private Boolean isCorrect;
    
    @Column
    private Integer timeSpent; // em segundos
    
    @Column
    private Integer xpEarned;
    
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    // Getters e Setters manuais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Task getTask() { return task; }
    public void setTask(Task task) { this.task = task; }
    
    public Integer getSelectedAnswerIndex() { return selectedAnswerIndex; }
    public void setSelectedAnswerIndex(Integer selectedAnswerIndex) { this.selectedAnswerIndex = selectedAnswerIndex; }
    
    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }
    
    public Integer getTimeSpent() { return timeSpent; }
    public void setTimeSpent(Integer timeSpent) { this.timeSpent = timeSpent; }
    
    public Integer getXpEarned() { return xpEarned; }
    public void setXpEarned(Integer xpEarned) { this.xpEarned = xpEarned; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // Método builder manual
    public static TaskAttemptBuilder builder() {
        return new TaskAttemptBuilder();
    }
    
    public static class TaskAttemptBuilder {
        private Long id;
        private User user;
        private Task task;
        private Integer selectedAnswerIndex;
        private Boolean isCorrect;
        private Integer timeSpent;
        private Integer xpEarned;
        private LocalDateTime createdAt;
        
        public TaskAttemptBuilder id(Long id) { this.id = id; return this; }
        public TaskAttemptBuilder user(User user) { this.user = user; return this; }
        public TaskAttemptBuilder task(Task task) { this.task = task; return this; }
        public TaskAttemptBuilder selectedAnswerIndex(Integer selectedAnswerIndex) { this.selectedAnswerIndex = selectedAnswerIndex; return this; }
        public TaskAttemptBuilder isCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; return this; }
        public TaskAttemptBuilder timeSpent(Integer timeSpent) { this.timeSpent = timeSpent; return this; }
        public TaskAttemptBuilder xpEarned(Integer xpEarned) { this.xpEarned = xpEarned; return this; }
        public TaskAttemptBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        
        public TaskAttempt build() {
            TaskAttempt attempt = new TaskAttempt();
            attempt.setId(id);
            attempt.setUser(user);
            attempt.setTask(task);
            attempt.setSelectedAnswerIndex(selectedAnswerIndex);
            attempt.setIsCorrect(isCorrect);
            attempt.setTimeSpent(timeSpent);
            attempt.setXpEarned(xpEarned);
            attempt.setCreatedAt(createdAt);
            return attempt;
        }
    }
}
