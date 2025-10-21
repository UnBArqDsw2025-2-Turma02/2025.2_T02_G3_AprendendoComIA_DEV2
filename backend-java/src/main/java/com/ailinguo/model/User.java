package com.ailinguo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    
    private String name;
    private String password; // bcrypt hash
    
    private CefrLevel cefrLevel;
    private Integer dailyGoalMinutes;
    private Integer streakDays;
    private Integer totalMinutes;
    
    private LocalDateTime createdAt;
    private LocalDateTime lastStudyDate;
    
    public enum CefrLevel {
        A1, A2, B1, B2, C1
    }
}

