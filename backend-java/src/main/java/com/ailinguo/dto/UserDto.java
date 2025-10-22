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
                .id(user.getId().toString())
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
    
    // Getters e Setters manuais
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public User.CefrLevel getCefrLevel() { return cefrLevel; }
    public void setCefrLevel(User.CefrLevel cefrLevel) { this.cefrLevel = cefrLevel; }
    
    public Integer getDailyGoalMinutes() { return dailyGoalMinutes; }
    public void setDailyGoalMinutes(Integer dailyGoalMinutes) { this.dailyGoalMinutes = dailyGoalMinutes; }
    
    public Integer getStreakDays() { return streakDays; }
    public void setStreakDays(Integer streakDays) { this.streakDays = streakDays; }
    
    public Integer getTotalMinutes() { return totalMinutes; }
    public void setTotalMinutes(Integer totalMinutes) { this.totalMinutes = totalMinutes; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getLastStudyDate() { return lastStudyDate; }
    public void setLastStudyDate(LocalDateTime lastStudyDate) { this.lastStudyDate = lastStudyDate; }
    
    // Método builder manual
    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }
    
    public static class UserDtoBuilder {
        private String id;
        private String email;
        private String name;
        private User.CefrLevel cefrLevel;
        private Integer dailyGoalMinutes;
        private Integer streakDays;
        private Integer totalMinutes;
        private LocalDateTime createdAt;
        private LocalDateTime lastStudyDate;
        
        public UserDtoBuilder id(String id) { this.id = id; return this; }
        public UserDtoBuilder email(String email) { this.email = email; return this; }
        public UserDtoBuilder name(String name) { this.name = name; return this; }
        public UserDtoBuilder cefrLevel(User.CefrLevel cefrLevel) { this.cefrLevel = cefrLevel; return this; }
        public UserDtoBuilder dailyGoalMinutes(Integer dailyGoalMinutes) { this.dailyGoalMinutes = dailyGoalMinutes; return this; }
        public UserDtoBuilder streakDays(Integer streakDays) { this.streakDays = streakDays; return this; }
        public UserDtoBuilder totalMinutes(Integer totalMinutes) { this.totalMinutes = totalMinutes; return this; }
        public UserDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public UserDtoBuilder lastStudyDate(LocalDateTime lastStudyDate) { this.lastStudyDate = lastStudyDate; return this; }
        
        public UserDto build() {
            UserDto dto = new UserDto();
            dto.setId(id);
            dto.setEmail(email);
            dto.setName(name);
            dto.setCefrLevel(cefrLevel);
            dto.setDailyGoalMinutes(dailyGoalMinutes);
            dto.setStreakDays(streakDays);
            dto.setTotalMinutes(totalMinutes);
            dto.setCreatedAt(createdAt);
            dto.setLastStudyDate(lastStudyDate);
            return dto;
        }
    }
}

