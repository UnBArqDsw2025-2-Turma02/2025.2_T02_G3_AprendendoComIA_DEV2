package com.ailinguo.controller;

import com.ailinguo.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    
    private final TaskService taskService;
    
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @GetMapping("/random/{userId}")
    public ResponseEntity<Map<String, Object>> getRandomTasks(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String type) {
        try {
            Map<String, Object> tasks = taskService.getRandomTasks(userId, limit, difficulty, type);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/attempt")
    public ResponseEntity<Map<String, Object>> submitTaskAttempt(@RequestBody Map<String, Object> request) {
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Long taskId = Long.valueOf(request.get("taskId").toString());
            Integer selectedAnswerIndex = Integer.valueOf(request.get("selectedAnswerIndex").toString());
            Integer timeSpent = request.get("timeSpent") != null ? 
                Integer.valueOf(request.get("timeSpent").toString()) : null;
            
            Map<String, Object> result = taskService.submitTaskAttempt(userId, taskId, selectedAnswerIndex, timeSpent);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/stats/{userId}")
    public ResponseEntity<Map<String, Object>> getUserTaskStats(@PathVariable Long userId) {
        try {
            Map<String, Object> stats = taskService.getUserTaskStats(userId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/history/{userId}")
    public ResponseEntity<Map<String, Object>> getUserTaskHistory(@PathVariable Long userId) {
        try {
            Map<String, Object> history = taskService.getUserTaskHistory(userId);
            return ResponseEntity.ok(history);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}