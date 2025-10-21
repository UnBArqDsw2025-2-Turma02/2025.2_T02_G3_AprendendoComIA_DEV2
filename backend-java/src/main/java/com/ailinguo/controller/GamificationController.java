package com.ailinguo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/gamification")
public class GamificationController {
    
    private final List<Map<String, Object>> leaders = new ArrayList<>();
    private final List<Map<String, Object>> groups = new ArrayList<>();
    private final List<Map<String, Object>> goals = new ArrayList<>();
    
    public GamificationController() {
        initializeMockData();
    }
    
    private void initializeMockData() {
        // Leaders
        leaders.add(Map.of(
                "id", "u1", "name", "Alice", "team", "Team Alpha", 
                "xp", 4820, "minutes", 310, "streak", 9,
                "reactions", Map.of("clap", 10, "fire", 8, "flex", 4, "lol", 2)
        ));
        leaders.add(Map.of(
                "id", "u2", "name", "Bruno", "team", "Grammar Ninjas",
                "xp", 4210, "minutes", 265, "streak", 7,
                "reactions", Map.of("clap", 6, "fire", 9, "flex", 3, "lol", 1)
        ));
        leaders.add(Map.of(
                "id", "u3", "name", "Carla", "team", "Pronunciation Pros",
                "xp", 3980, "minutes", 248, "streak", 8,
                "reactions", Map.of("clap", 4, "fire", 7, "flex", 2, "lol", 5)
        ));
        
        // Groups
        groups.add(Map.of("id", "t1", "name", "Team Alpha", "members", 18, "open", true));
        groups.add(Map.of("id", "t2", "name", "Grammar Ninjas", "members", 12, "open", true));
        groups.add(Map.of("id", "t3", "name", "Pronunciation Pros", "members", 9, "open", false));
        
        // Goals
        goals.add(Map.of(
                "id", "g1", "title", "Ficar ativo por 7 dias", 
                "target", 7, "unit", "days", "progress", 2,
                "desc", "Entre todos os dias nesta semana.", "icon", "Shield"
        ));
        goals.add(Map.of(
                "id", "g2", "title", "60 min no Tutor IA",
                "target", 60, "unit", "min", "progress", 15,
                "desc", "Tempo total em conversas.", "icon", "Rocket"
        ));
        goals.add(Map.of(
                "id", "g3", "title", "2 horas em atividades",
                "target", 120, "unit", "min", "progress", 35,
                "desc", "Tarefas e exercícios.", "icon", "Flame"
        ));
    }
    
    @GetMapping("/leaderboard")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getLeaderboard() {
        return ResponseEntity.ok(Map.of("leaders", leaders));
    }
    
    @GetMapping("/groups")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getGroups() {
        return ResponseEntity.ok(Map.of("groups", groups));
    }
    
    @PostMapping("/groups")
    public ResponseEntity<?> manageGroup(@RequestBody Map<String, Object> request) {
        String action = (String) request.get("action");
        
        if ("create".equals(action) && request.containsKey("name")) {
            String name = (String) request.get("name");
            Map<String, Object> newGroup = new HashMap<>();
            newGroup.put("id", UUID.randomUUID().toString().substring(0, 7));
            newGroup.put("name", name);
            newGroup.put("members", 1);
            newGroup.put("open", true);
            groups.add(0, newGroup);
            return ResponseEntity.ok(Map.of("ok", true, "group", newGroup));
        }
        
        if ("join".equals(action) && request.containsKey("groupId")) {
            String groupId = (String) request.get("groupId");
            Optional<Map<String, Object>> groupOpt = groups.stream()
                    .filter(g -> g.get("id").equals(groupId))
                    .findFirst();
            
            if (groupOpt.isPresent()) {
                Map<String, Object> group = groupOpt.get();
                if (!(Boolean) group.get("open")) {
                    return ResponseEntity.badRequest().body(Map.of("error", "closed"));
                }
                group.put("members", (Integer) group.get("members") + 1);
                return ResponseEntity.ok(Map.of("ok", true, "group", group));
            }
            return ResponseEntity.status(404).body(Map.of("error", "not found"));
        }
        
        return ResponseEntity.badRequest().body(Map.of("error", "bad request"));
    }
    
    @GetMapping("/goals")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getGoals() {
        return ResponseEntity.ok(Map.of("goals", goals));
    }
    
    @PostMapping("/react")
    public ResponseEntity<Map<String, Boolean>> addReaction(@RequestBody Map<String, Object> request) {
        // Mock implementation - in production, save to database
        return ResponseEntity.ok(Map.of("success", true));
    }
}

