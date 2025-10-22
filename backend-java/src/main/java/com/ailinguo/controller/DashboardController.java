package com.ailinguo.controller;

import com.ailinguo.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping("/stats/{userId}")
    public ResponseEntity<Map<String, Object>> getUserStats(@PathVariable Long userId) {
        try {
            Map<String, Object> stats = dashboardService.getUserStats(userId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/progress/{userId}")
    public ResponseEntity<Map<String, Object>> getUserProgress(@PathVariable Long userId) {
        try {
            Map<String, Object> progress = dashboardService.getUserProgress(userId);
            return ResponseEntity.ok(progress);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/achievements/{userId}")
    public ResponseEntity<Map<String, Object>> getUserAchievements(@PathVariable Long userId) {
        try {
            Map<String, Object> achievements = dashboardService.getUserAchievements(userId);
            return ResponseEntity.ok(achievements);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
