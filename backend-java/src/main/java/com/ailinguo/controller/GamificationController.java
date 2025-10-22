package com.ailinguo.controller;

import com.ailinguo.service.GamificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/gamification")
@CrossOrigin(origins = "*")
public class GamificationController {
    
    private final GamificationService gamificationService;
    
    public GamificationController(GamificationService gamificationService) {
        this.gamificationService = gamificationService;
    }
    
    @GetMapping("/leaderboard")
    public ResponseEntity<Map<String, Object>> getLeaderboard(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String period) {
        try {
            Map<String, Object> leaderboard = gamificationService.getLeaderboard(limit, period);
            return ResponseEntity.ok(leaderboard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/user-ranking/{userId}")
    public ResponseEntity<Map<String, Object>> getUserRanking(@PathVariable Long userId) {
        try {
            Map<String, Object> ranking = gamificationService.getUserRanking(userId);
            return ResponseEntity.ok(ranking);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/goals/{userId}")
    public ResponseEntity<Map<String, Object>> getUserGoals(@PathVariable Long userId) {
        try {
            Map<String, Object> goals = gamificationService.getUserGoals(userId);
            return ResponseEntity.ok(goals);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/groups")
    public ResponseEntity<Map<String, Object>> getGroups() {
        try {
            Map<String, Object> groups = gamificationService.getGroups();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/join-group/{userId}/{groupId}")
    public ResponseEntity<Map<String, Object>> joinGroup(@PathVariable Long userId, @PathVariable Long groupId) {
        try {
            Map<String, Object> result = gamificationService.joinGroup(userId, groupId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}