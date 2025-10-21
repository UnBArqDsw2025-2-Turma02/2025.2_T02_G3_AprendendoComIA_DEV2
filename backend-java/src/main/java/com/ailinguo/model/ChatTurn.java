package com.ailinguo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatTurns")
public class ChatTurn {
    @Id
    private String id;
    
    private String sessionId;
    private String userId;
    private Role role;
    private String text;
    
    private List<Correction> corrections;
    private MiniExercise exercise;
    
    private LocalDateTime createdAt;
    
    public enum Role {
        USER, TUTOR
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Correction {
        private String original;
        private String corrected;
        private String explanation;
        private String rule;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniExercise {
        private String type;
        private String question;
        private List<String> options;
        private Integer correct;
        private String explanation;
    }
}

