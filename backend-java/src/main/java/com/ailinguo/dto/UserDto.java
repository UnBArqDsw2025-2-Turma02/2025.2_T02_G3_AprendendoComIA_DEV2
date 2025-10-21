package com.ailinguo.dto;

import com.ailinguo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String email;
    private String name;
    private User.CefrLevel cefrLevel;
    private Integer dailyGoalMinutes;
    private Integer streakDays;
    private Integer totalMinutes;
    private LocalDateTime createdAt;
    private LocalDateTime lastStudyDate;
    
    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .cefrLevel(user.getCefrLevel())
                .dailyGoalMinutes(user.getDailyGoalMinutes())
                .streakDays(user.getStreakDays())
                .totalMinutes(user.getTotalMinutes())
                .createdAt(user.getCreatedAt())
                .lastStudyDate(user.getLastStudyDate())
                .build();
    }
}

