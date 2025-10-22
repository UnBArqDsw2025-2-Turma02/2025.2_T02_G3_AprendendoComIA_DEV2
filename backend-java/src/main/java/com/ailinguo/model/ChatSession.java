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
@Table(name = "chat_sessions")
@EntityListeners(AuditingEntityListener.class)
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    private User.CefrLevel level;
    
    @Column(columnDefinition = "TEXT")
    private String topic;
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Relacionamentos
    @OneToMany(mappedBy = "chatSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatTurn> chatTurns;
    
    // Getters e Setters manuais
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public User.CefrLevel getLevel() { return level; }
    public void setLevel(User.CefrLevel level) { this.level = level; }
    
    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public List<ChatTurn> getChatTurns() { return chatTurns; }
    public void setChatTurns(List<ChatTurn> chatTurns) { this.chatTurns = chatTurns; }
    
    // Método builder manual
    public static ChatSessionBuilder builder() {
        return new ChatSessionBuilder();
    }
    
    public static class ChatSessionBuilder {
        private Long id;
        private User user;
        private User.CefrLevel level;
        private String topic;
        private String summary;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<ChatTurn> chatTurns;
        
        public ChatSessionBuilder id(Long id) { this.id = id; return this; }
        public ChatSessionBuilder user(User user) { this.user = user; return this; }
        public ChatSessionBuilder level(User.CefrLevel level) { this.level = level; return this; }
        public ChatSessionBuilder topic(String topic) { this.topic = topic; return this; }
        public ChatSessionBuilder summary(String summary) { this.summary = summary; return this; }
        public ChatSessionBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ChatSessionBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public ChatSessionBuilder chatTurns(List<ChatTurn> chatTurns) { this.chatTurns = chatTurns; return this; }
        
        public ChatSession build() {
            ChatSession session = new ChatSession();
            session.setId(id);
            session.setUser(user);
            session.setLevel(level);
            session.setTopic(topic);
            session.setSummary(summary);
            session.setCreatedAt(createdAt);
            session.setUpdatedAt(updatedAt);
            session.setChatTurns(chatTurns);
            return session;
        }
    }
}

