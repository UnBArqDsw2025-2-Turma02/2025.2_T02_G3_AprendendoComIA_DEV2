package com.ailinguo.controller;

import com.ailinguo.service.UserSettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user-settings")
@CrossOrigin(origins = "*")
public class UserSettingsController {
    
    private final UserSettingsService userSettingsService;
    
    public UserSettingsController(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }
    
    @GetMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> getUserProfile(@PathVariable Long userId) {
        try {
            Map<String, Object> profile = userSettingsService.getUserProfile(userId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/profile/{userId}")
    public ResponseEntity<Map<String, Object>> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> profileData) {
        try {
            Map<String, Object> result = userSettingsService.updateUserProfile(userId, profileData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/preferences/{userId}")
    public ResponseEntity<Map<String, Object>> getUserPreferences(@PathVariable Long userId) {
        try {
            Map<String, Object> preferences = userSettingsService.getUserPreferences(userId);
            return ResponseEntity.ok(preferences);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/preferences/{userId}")
    public ResponseEntity<Map<String, Object>> updateUserPreferences(
            @PathVariable Long userId,
            @RequestBody Map<String, Object> preferencesData) {
        try {
            Map<String, Object> result = userSettingsService.updateUserPreferences(userId, preferencesData);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/change-password/{userId}")
    public ResponseEntity<Map<String, Object>> changePassword(
            @PathVariable Long userId,
            @RequestBody Map<String, String> passwordData) {
        try {
            String currentPassword = passwordData.get("currentPassword");
            String newPassword = passwordData.get("newPassword");
            
            Map<String, Object> result = userSettingsService.changePassword(userId, currentPassword, newPassword);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/account/{userId}")
    public ResponseEntity<Map<String, Object>> deleteAccount(@PathVariable Long userId) {
        try {
            Map<String, Object> result = userSettingsService.deleteAccount(userId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
