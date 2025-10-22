package com.ailinguo.service;

import com.ailinguo.model.User;
import com.ailinguo.model.Group;
import com.ailinguo.repository.UserRepository;
import com.ailinguo.repository.GroupRepository;
import com.ailinguo.repository.SrsReviewRepository;
import com.ailinguo.repository.ChatTurnRepository;
import com.ailinguo.repository.TaskAttemptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GamificationService {
    
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final SrsReviewRepository srsReviewRepository;
    private final ChatTurnRepository chatTurnRepository;
    private final TaskAttemptRepository taskAttemptRepository;
    
    public GamificationService(UserRepository userRepository,
                              GroupRepository groupRepository,
                              SrsReviewRepository srsReviewRepository,
                              ChatTurnRepository chatTurnRepository,
                              TaskAttemptRepository taskAttemptRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.srsReviewRepository = srsReviewRepository;
        this.chatTurnRepository = chatTurnRepository;
        this.taskAttemptRepository = taskAttemptRepository;
    }
    
    public Map<String, Object> getLeaderboard(int limit, String period) {
        List<User> allUsers = userRepository.findAll();
        List<Map<String, Object>> leaderboard = new ArrayList<>();
        
        for (User user : allUsers) {
            long xp = calculateUserXP(user.getId());
            long streak = calculateUserStreak(user.getId());
            
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("name", user.getName());
            userData.put("email", user.getEmail());
            userData.put("cefrLevel", user.getCefrLevel().toString());
            userData.put("xp", xp);
            userData.put("streak", streak);
            userData.put("totalMinutes", user.getTotalMinutes());
            
            leaderboard.add(userData);
        }
        
        // Ordenar por XP
        leaderboard.sort((a, b) -> Long.compare((Long) b.get("xp"), (Long) a.get("xp")));
        
        // Limitar resultados
        if (leaderboard.size() > limit) {
            leaderboard = leaderboard.subList(0, limit);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("leaderboard", leaderboard);
        result.put("totalUsers", allUsers.size());
        result.put("period", period != null ? period : "all");
        
        return result;
    }
    
    public Map<String, Object> getUserRanking(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<User> allUsers = userRepository.findAll();
        List<Map<String, Object>> allUserData = new ArrayList<>();
        
        for (User u : allUsers) {
            long xp = calculateUserXP(u.getId());
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", u.getId());
            userData.put("xp", xp);
            allUserData.add(userData);
        }
        
        // Ordenar por XP
        allUserData.sort((a, b) -> Long.compare((Long) b.get("xp"), (Long) a.get("xp")));
        
        // Encontrar posição do usuário
        int userRank = 1;
        for (Map<String, Object> userData : allUserData) {
            if (userData.get("id").equals(userId)) {
                break;
            }
            userRank++;
        }
        
        long userXP = calculateUserXP(userId);
        long userStreak = calculateUserStreak(userId);
        
        Map<String, Object> result = new HashMap<>();
        result.put("rank", userRank);
        result.put("totalUsers", allUsers.size());
        result.put("xp", userXP);
        result.put("streak", userStreak);
        result.put("cefrLevel", user.getCefrLevel().toString());
        
        return result;
    }
    
    public Map<String, Object> getUserGoals(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Map<String, Object>> goals = new ArrayList<>();
        
        // Meta: Streak de 7 dias
        long currentStreak = calculateUserStreak(userId);
        Map<String, Object> streakGoal = new HashMap<>();
        streakGoal.put("id", "streak_7_days");
        streakGoal.put("title", "Streak de 7 dias");
        streakGoal.put("description", "Estude inglês por 7 dias consecutivos");
        streakGoal.put("progress", Math.min(currentStreak, 7));
        streakGoal.put("target", 7);
        streakGoal.put("unit", "dias");
        streakGoal.put("completed", currentStreak >= 7);
        streakGoal.put("icon", "flame");
        streakGoal.put("color", "orange");
        goals.add(streakGoal);
        
        // Meta: 1000 XP
        long currentXP = calculateUserXP(userId);
        Map<String, Object> xpGoal = new HashMap<>();
        xpGoal.put("id", "xp_1000");
        xpGoal.put("title", "1000 XP");
        xpGoal.put("description", "Ganhe 1000 pontos de experiência");
        xpGoal.put("progress", Math.min(currentXP, 1000));
        xpGoal.put("target", 1000);
        xpGoal.put("unit", "XP");
        xpGoal.put("completed", currentXP >= 1000);
        xpGoal.put("icon", "zap");
        xpGoal.put("color", "blue");
        goals.add(xpGoal);
        
        // Meta: 50 Lições
        long totalReviews = srsReviewRepository.countByUserId(userId);
        Map<String, Object> lessonsGoal = new HashMap<>();
        lessonsGoal.put("id", "lessons_50");
        lessonsGoal.put("title", "50 Lições");
        lessonsGoal.put("description", "Complete 50 lições de vocabulário");
        lessonsGoal.put("progress", Math.min(totalReviews, 50));
        lessonsGoal.put("target", 50);
        lessonsGoal.put("unit", "lições");
        lessonsGoal.put("completed", totalReviews >= 50);
        lessonsGoal.put("icon", "book");
        lessonsGoal.put("color", "green");
        goals.add(lessonsGoal);
        
        // Meta: 10 horas de conversação
        long totalChatTurns = chatTurnRepository.countByUserId(userId);
        long chatHours = totalChatTurns * 3 / 60; // Estimativa: 3 minutos por turno
        Map<String, Object> chatGoal = new HashMap<>();
        chatGoal.put("id", "chat_10_hours");
        chatGoal.put("title", "10 horas de conversação");
        chatGoal.put("description", "Pratique conversação por 10 horas");
        chatGoal.put("progress", Math.min(chatHours, 10));
        chatGoal.put("target", 10);
        chatGoal.put("unit", "horas");
        chatGoal.put("completed", chatHours >= 10);
        chatGoal.put("icon", "message");
        chatGoal.put("color", "purple");
        goals.add(chatGoal);
        
        Map<String, Object> result = new HashMap<>();
        result.put("goals", goals);
        result.put("totalGoals", goals.size());
        result.put("completedGoals", goals.stream().mapToInt(g -> (Boolean) g.get("completed") ? 1 : 0).sum());
        
        return result;
    }
    
    public Map<String, Object> getGroups() {
        List<Group> groups = groupRepository.findAll();
        List<Map<String, Object>> groupList = new ArrayList<>();
        
        for (Group group : groups) {
            Map<String, Object> groupData = new HashMap<>();
            groupData.put("id", group.getId());
            groupData.put("name", group.getName());
            groupData.put("description", group.getDescription());
            groupData.put("isOpen", group.getOpen());
            groupData.put("memberCount", group.getMembers());
            groupData.put("createdAt", group.getCreatedAt());
            groupList.add(groupData);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("groups", groupList);
        result.put("totalGroups", groupList.size());
        
        return result;
    }
    
    public Map<String, Object> joinGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        
        if (!group.getOpen()) {
            throw new RuntimeException("Group is not open for new members");
        }
        
        // Adicionar usuário ao grupo (implementação simplificada)
        group.setMembers(group.getMembers() + 1);
        groupRepository.save(group);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Successfully joined group");
        result.put("groupId", groupId);
        result.put("groupName", group.getName());
        
        return result;
    }
    
    private long calculateUserXP(Long userId) {
        long vocabularyXP = srsReviewRepository.countByUserId(userId) * 10;
        long chatXP = chatTurnRepository.countByUserId(userId) * 5;
        long taskXP = taskAttemptRepository.countByUserId(userId) * 15;
        
        return vocabularyXP + chatXP + taskXP;
    }
    
    private long calculateUserStreak(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        long streak = 0;
        
        for (int i = 0; i < 365; i++) {
            LocalDateTime day = now.minusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime nextDay = day.plusDays(1);
            
            long dayReviews = srsReviewRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            long dayChatTurns = chatTurnRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            long dayTasks = taskAttemptRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            
            if (dayReviews > 0 || dayChatTurns > 0 || dayTasks > 0) {
                streak++;
            } else {
                break;
            }
        }
        
        return streak;
    }
}
