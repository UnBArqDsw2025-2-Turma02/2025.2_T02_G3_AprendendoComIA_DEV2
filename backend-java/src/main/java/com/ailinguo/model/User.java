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
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String password; // bcrypt hash
    
    @Enumerated(EnumType.STRING)
    private CefrLevel cefrLevel;
    
    private Integer dailyGoalMinutes;
    private Integer streakDays;
    private Integer totalMinutes;
    private Integer totalXp = 0;
    private Integer level = 1;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Método para compatibilidade com AdminService
    public LocalDateTime getLastModifiedAt() {
        return updatedAt;
    }
    
    // Getters e Setters manuais para garantir compatibilidade
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public CefrLevel getCefrLevel() { return cefrLevel; }
    public void setCefrLevel(CefrLevel cefrLevel) { this.cefrLevel = cefrLevel; }
    
    public Integer getDailyGoalMinutes() { return dailyGoalMinutes; }
    public void setDailyGoalMinutes(Integer dailyGoalMinutes) { this.dailyGoalMinutes = dailyGoalMinutes; }
    
    public Integer getStreakDays() { return streakDays; }
    public void setStreakDays(Integer streakDays) { this.streakDays = streakDays; }
    
    public Integer getTotalMinutes() { return totalMinutes; }
    public void setTotalMinutes(Integer totalMinutes) { this.totalMinutes = totalMinutes; }
    
    public Integer getTotalXp() { return totalXp; }
    public void setTotalXp(Integer totalXp) { this.totalXp = totalXp; }
    
    public Integer getLevel() { return level; }
    public void setLevel(Integer level) { this.level = level; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<ChatSession> getChatSessions() { return chatSessions; }
    public void setChatSessions(List<ChatSession> chatSessions) { this.chatSessions = chatSessions; }
    
    public List<SrsReview> getSrsReviews() { return srsReviews; }
    public void setSrsReviews(List<SrsReview> srsReviews) { this.srsReviews = srsReviews; }
    
    public List<ChatTurn> getChatTurns() { return chatTurns; }
    public void setChatTurns(List<ChatTurn> chatTurns) { this.chatTurns = chatTurns; }
    
    public LocalDateTime getLastStudyDate() { return lastStudyDate; }
    public void setLastStudyDate(LocalDateTime lastStudyDate) { this.lastStudyDate = lastStudyDate; }
    
    private LocalDateTime lastStudyDate;
    
    // Relacionamentos
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatSession> chatSessions;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SrsReview> srsReviews;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatTurn> chatTurns;
    
    public enum CefrLevel {
        A1, A2, B1, B2, C1
    }
    
    // Método builder manual
    public static UserBuilder builder() {
        return new UserBuilder();
    }
    
    public static class UserBuilder {
        private Long id;
        private String email;
        private String name;
        private String password;
        private CefrLevel cefrLevel;
        private Integer dailyGoalMinutes;
        private Integer streakDays;
        private Integer totalMinutes;
        private Integer totalXp;
        private Integer level;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private LocalDateTime lastStudyDate;
        private List<ChatSession> chatSessions;
        private List<SrsReview> srsReviews;
        private List<ChatTurn> chatTurns;
        
        public UserBuilder id(Long id) { this.id = id; return this; }
        public UserBuilder email(String email) { this.email = email; return this; }
        public UserBuilder name(String name) { this.name = name; return this; }
        public UserBuilder password(String password) { this.password = password; return this; }
        public UserBuilder cefrLevel(CefrLevel cefrLevel) { this.cefrLevel = cefrLevel; return this; }
        public UserBuilder dailyGoalMinutes(Integer dailyGoalMinutes) { this.dailyGoalMinutes = dailyGoalMinutes; return this; }
        public UserBuilder streakDays(Integer streakDays) { this.streakDays = streakDays; return this; }
        public UserBuilder totalMinutes(Integer totalMinutes) { this.totalMinutes = totalMinutes; return this; }
        public UserBuilder totalXp(Integer totalXp) { this.totalXp = totalXp; return this; }
        public UserBuilder level(Integer level) { this.level = level; return this; }
        public UserBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public UserBuilder lastStudyDate(LocalDateTime lastStudyDate) { this.lastStudyDate = lastStudyDate; return this; }
        public UserBuilder chatSessions(List<ChatSession> chatSessions) { this.chatSessions = chatSessions; return this; }
        public UserBuilder srsReviews(List<SrsReview> srsReviews) { this.srsReviews = srsReviews; return this; }
        public UserBuilder chatTurns(List<ChatTurn> chatTurns) { this.chatTurns = chatTurns; return this; }
        
        public User build() {
            User user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setName(name);
            user.setPassword(password);
            user.setCefrLevel(cefrLevel);
            user.setDailyGoalMinutes(dailyGoalMinutes);
            user.setStreakDays(streakDays);
            user.setTotalMinutes(totalMinutes);
            user.setTotalXp(totalXp);
            user.setLevel(level);
            user.setCreatedAt(createdAt);
            user.setUpdatedAt(updatedAt);
            user.setLastStudyDate(lastStudyDate);
            user.setChatSessions(chatSessions);
            user.setSrsReviews(srsReviews);
            user.setChatTurns(chatTurns);
            return user;
        }
    }
}

