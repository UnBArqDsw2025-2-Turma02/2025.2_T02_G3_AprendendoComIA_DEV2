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
@Table(name = "chat_turns")
@EntityListeners(AuditingEntityListener.class)
public class ChatTurn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    private ChatSession chatSession;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(columnDefinition = "TEXT")
    private String text;
    
    @OneToMany(mappedBy = "chatTurn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Correction> corrections;
    
    @OneToOne(mappedBy = "chatTurn", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MiniExercise exercise;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum Role {
        USER, TUTOR
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "corrections")
    public static class Correction {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "chat_turn_id", nullable = false)
        private ChatTurn chatTurn;
        
        @Column(columnDefinition = "TEXT")
        private String original;
        
        @Column(columnDefinition = "TEXT")
        private String corrected;
        
        @Column(columnDefinition = "TEXT")
        private String explanation;
        
        @Column(columnDefinition = "TEXT")
        private String rule;
        
        // Getters e Setters manuais para Correction
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public ChatTurn getChatTurn() { return chatTurn; }
        public void setChatTurn(ChatTurn chatTurn) { this.chatTurn = chatTurn; }
        
        public String getOriginal() { return original; }
        public void setOriginal(String original) { this.original = original; }
        
        public String getCorrected() { return corrected; }
        public void setCorrected(String corrected) { this.corrected = corrected; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
        
        public String getRule() { return rule; }
        public void setRule(String rule) { this.rule = rule; }
        
        // Método builder manual
        public static CorrectionBuilder builder() {
            return new CorrectionBuilder();
        }
        
        public static class CorrectionBuilder {
            private Long id;
            private ChatTurn chatTurn;
            private String original;
            private String corrected;
            private String explanation;
            private String rule;
            
            public CorrectionBuilder id(Long id) { this.id = id; return this; }
            public CorrectionBuilder chatTurn(ChatTurn chatTurn) { this.chatTurn = chatTurn; return this; }
            public CorrectionBuilder original(String original) { this.original = original; return this; }
            public CorrectionBuilder corrected(String corrected) { this.corrected = corrected; return this; }
            public CorrectionBuilder explanation(String explanation) { this.explanation = explanation; return this; }
            public CorrectionBuilder rule(String rule) { this.rule = rule; return this; }
            
            public Correction build() {
                Correction correction = new Correction();
                correction.setId(id);
                correction.setChatTurn(chatTurn);
                correction.setOriginal(original);
                correction.setCorrected(corrected);
                correction.setExplanation(explanation);
                correction.setRule(rule);
                return correction;
            }
        }
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Entity
    @Table(name = "mini_exercises")
    public static class MiniExercise {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        
        @OneToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "chat_turn_id", nullable = false)
        private ChatTurn chatTurn;
        
        private String type;
        
        @Column(columnDefinition = "TEXT")
        private String question;
        
        @ElementCollection
        @CollectionTable(name = "exercise_options", joinColumns = @JoinColumn(name = "exercise_id"))
        @Column(name = "option_text")
        private List<String> options;
        
        private Integer correct;
        
        @Column(columnDefinition = "TEXT")
        private String explanation;
        
        // Getters e Setters manuais para MiniExercise
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public ChatTurn getChatTurn() { return chatTurn; }
        public void setChatTurn(ChatTurn chatTurn) { this.chatTurn = chatTurn; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        
        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }
        
        public Integer getCorrect() { return correct; }
        public void setCorrect(Integer correct) { this.correct = correct; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
        
        // Método builder manual
        public static MiniExerciseBuilder builder() {
            return new MiniExerciseBuilder();
        }
        
        public static class MiniExerciseBuilder {
            private Long id;
            private ChatTurn chatTurn;
            private String type;
            private String question;
            private List<String> options;
            private Integer correct;
            private String explanation;
            
            public MiniExerciseBuilder id(Long id) { this.id = id; return this; }
            public MiniExerciseBuilder chatTurn(ChatTurn chatTurn) { this.chatTurn = chatTurn; return this; }
            public MiniExerciseBuilder type(String type) { this.type = type; return this; }
            public MiniExerciseBuilder question(String question) { this.question = question; return this; }
            public MiniExerciseBuilder options(List<String> options) { this.options = options; return this; }
            public MiniExerciseBuilder correct(Integer correct) { this.correct = correct; return this; }
            public MiniExerciseBuilder explanation(String explanation) { this.explanation = explanation; return this; }
            
            public MiniExercise build() {
                MiniExercise exercise = new MiniExercise();
                exercise.setId(id);
                exercise.setChatTurn(chatTurn);
                exercise.setType(type);
                exercise.setQuestion(question);
                exercise.setOptions(options);
                exercise.setCorrect(correct);
                exercise.setExplanation(explanation);
                return exercise;
            }
        }
    }
    
    // Getters e Setters manuais para ChatTurn
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public ChatSession getChatSession() { return chatSession; }
    public void setChatSession(ChatSession chatSession) { this.chatSession = chatSession; }
    
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public List<Correction> getCorrections() { return corrections; }
    public void setCorrections(List<Correction> corrections) { this.corrections = corrections; }
    
    public MiniExercise getExercise() { return exercise; }
    public void setExercise(MiniExercise exercise) { this.exercise = exercise; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    // Método builder manual
    public static ChatTurnBuilder builder() {
        return new ChatTurnBuilder();
    }
    
    public static class ChatTurnBuilder {
        private Long id;
        private ChatSession chatSession;
        private User user;
        private Role role;
        private String text;
        private List<Correction> corrections;
        private MiniExercise exercise;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        
        public ChatTurnBuilder id(Long id) { this.id = id; return this; }
        public ChatTurnBuilder chatSession(ChatSession chatSession) { this.chatSession = chatSession; return this; }
        public ChatTurnBuilder user(User user) { this.user = user; return this; }
        public ChatTurnBuilder role(Role role) { this.role = role; return this; }
        public ChatTurnBuilder text(String text) { this.text = text; return this; }
        public ChatTurnBuilder corrections(List<Correction> corrections) { this.corrections = corrections; return this; }
        public ChatTurnBuilder exercise(MiniExercise exercise) { this.exercise = exercise; return this; }
        public ChatTurnBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ChatTurnBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        
        public ChatTurn build() {
            ChatTurn turn = new ChatTurn();
            turn.setId(id);
            turn.setChatSession(chatSession);
            turn.setUser(user);
            turn.setRole(role);
            turn.setText(text);
            turn.setCorrections(corrections);
            turn.setExercise(exercise);
            turn.setCreatedAt(createdAt);
            turn.setUpdatedAt(updatedAt);
            return turn;
        }
    }
}

