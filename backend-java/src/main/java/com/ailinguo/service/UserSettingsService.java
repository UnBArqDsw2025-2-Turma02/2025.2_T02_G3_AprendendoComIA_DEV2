package com.ailinguo.service;

import com.ailinguo.model.User;
import com.ailinguo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSettingsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserSettingsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public Map<String, Object> getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, Object> profile = new HashMap<>();
        profile.put("id", user.getId());
        profile.put("name", user.getName());
        profile.put("email", user.getEmail());
        profile.put("cefrLevel", user.getCefrLevel().toString());
        profile.put("dailyGoalMinutes", user.getDailyGoalMinutes());
        profile.put("streakDays", user.getStreakDays());
        profile.put("totalMinutes", user.getTotalMinutes());
        profile.put("createdAt", user.getCreatedAt());
        profile.put("lastModifiedAt", user.getLastModifiedAt());
        
        return profile;
    }
    
    public Map<String, Object> updateUserProfile(Long userId, Map<String, Object> profileData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Atualizar campos permitidos
        if (profileData.containsKey("name")) {
            user.setName(profileData.get("name").toString());
        }
        
        if (profileData.containsKey("email")) {
            String newEmail = profileData.get("email").toString();
            // Verificar se o email já existe
            if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(newEmail);
        }
        
        if (profileData.containsKey("cefrLevel")) {
            try {
                User.CefrLevel level = User.CefrLevel.valueOf(profileData.get("cefrLevel").toString());
                user.setCefrLevel(level);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid CEFR level");
            }
        }
        
        if (profileData.containsKey("dailyGoalMinutes")) {
            try {
                Integer goal = Integer.valueOf(profileData.get("dailyGoalMinutes").toString());
                if (goal < 5 || goal > 120) {
                    throw new RuntimeException("Daily goal must be between 5 and 120 minutes");
                }
                user.setDailyGoalMinutes(goal);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid daily goal format");
            }
        }
        
        userRepository.save(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Profile updated successfully");
        result.put("user", getUserProfile(userId));
        
        return result;
    }
    
    public Map<String, Object> getUserPreferences(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Map<String, Object> preferences = new HashMap<>();
        preferences.put("cefrLevel", user.getCefrLevel().toString());
        preferences.put("dailyGoalMinutes", user.getDailyGoalMinutes());
        preferences.put("notifications", Map.of(
            "email", true,
            "push", true,
            "weekly", true,
            "achievements", true,
            "reminders", true
        ));
        preferences.put("privacy", Map.of(
            "publicProfile", true,
            "shareProgress", true,
            "analytics", false
        ));
        preferences.put("appearance", Map.of(
            "theme", "light",
            "language", "pt-BR",
            "timezone", "America/Sao_Paulo"
        ));
        preferences.put("learning", Map.of(
            "sound", true,
            "vibration", true,
            "autoPlay", false
        ));
        
        return preferences;
    }
    
    public Map<String, Object> updateUserPreferences(Long userId, Map<String, Object> preferencesData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Atualizar preferências de aprendizado
        if (preferencesData.containsKey("cefrLevel")) {
            try {
                User.CefrLevel level = User.CefrLevel.valueOf(preferencesData.get("cefrLevel").toString());
                user.setCefrLevel(level);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid CEFR level");
            }
        }
        
        if (preferencesData.containsKey("dailyGoalMinutes")) {
            try {
                Integer goal = Integer.valueOf(preferencesData.get("dailyGoalMinutes").toString());
                if (goal < 5 || goal > 120) {
                    throw new RuntimeException("Daily goal must be between 5 and 120 minutes");
                }
                user.setDailyGoalMinutes(goal);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid daily goal format");
            }
        }
        
        userRepository.save(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Preferences updated successfully");
        result.put("preferences", getUserPreferences(userId));
        
        return result;
    }
    
    public Map<String, Object> changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Verificar senha atual
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }
        
        // Validar nova senha
        if (newPassword == null || newPassword.length() < 6) {
            throw new RuntimeException("New password must be at least 6 characters long");
        }
        
        // Atualizar senha
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Password changed successfully");
        
        return result;
    }
    
    public Map<String, Object> deleteAccount(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Em uma implementação real, você também deletaria dados relacionados
        // como tentativas de tarefas, revisões, sessões de chat, etc.
        
        userRepository.delete(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Account deleted successfully");
        
        return result;
    }
}
