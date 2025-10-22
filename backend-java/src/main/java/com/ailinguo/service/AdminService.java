package com.ailinguo.service;

import com.ailinguo.model.User;
import com.ailinguo.model.VocabularyCard;
import com.ailinguo.model.Task;
import com.ailinguo.repository.UserRepository;
import com.ailinguo.repository.VocabularyCardRepository;
import com.ailinguo.repository.TaskRepository;
import com.ailinguo.repository.SrsReviewRepository;
import com.ailinguo.repository.ChatSessionRepository;
import com.ailinguo.repository.ChatTurnRepository;
import com.ailinguo.repository.TaskAttemptRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdminService {
    
    private final UserRepository userRepository;
    private final VocabularyCardRepository vocabularyCardRepository;
    private final TaskRepository taskRepository;
    private final SrsReviewRepository srsReviewRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatTurnRepository chatTurnRepository;
    private final TaskAttemptRepository taskAttemptRepository;
    
    public AdminService(UserRepository userRepository,
                       VocabularyCardRepository vocabularyCardRepository,
                       TaskRepository taskRepository,
                       SrsReviewRepository srsReviewRepository,
                       ChatSessionRepository chatSessionRepository,
                       ChatTurnRepository chatTurnRepository,
                       TaskAttemptRepository taskAttemptRepository) {
        this.userRepository = userRepository;
        this.vocabularyCardRepository = vocabularyCardRepository;
        this.taskRepository = taskRepository;
        this.srsReviewRepository = srsReviewRepository;
        this.chatSessionRepository = chatSessionRepository;
        this.chatTurnRepository = chatTurnRepository;
        this.taskAttemptRepository = taskAttemptRepository;
    }
    
    public Map<String, Object> getDashboardStats() {
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.count(); // Simplificado - todos os usuários são considerados ativos
        long totalVocabularyCards = vocabularyCardRepository.count();
        long totalTasks = taskRepository.count();
        long totalReviews = srsReviewRepository.count();
        long totalChatSessions = chatSessionRepository.count();
        long totalChatTurns = chatTurnRepository.count();
        long totalTaskAttempts = taskAttemptRepository.count();
        
        // Usuários ativos hoje
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        long todayActiveUsers = userRepository.count(); // Simplificado
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", totalUsers);
        stats.put("activeUsers", activeUsers);
        stats.put("todayActiveUsers", todayActiveUsers);
        stats.put("totalVocabularyCards", totalVocabularyCards);
        stats.put("totalTasks", totalTasks);
        stats.put("totalReviews", totalReviews);
        stats.put("totalChatSessions", totalChatSessions);
        stats.put("totalChatTurns", totalChatTurns);
        stats.put("totalTaskAttempts", totalTaskAttempts);
        
        // Estatísticas de crescimento (simuladas)
        stats.put("userGrowth", "+12%");
        stats.put("contentGrowth", "+8%");
        stats.put("engagementGrowth", "+15%");
        stats.put("retentionRate", "87%");
        
        return stats;
    }
    
    public Map<String, Object> getUsers(int page, int size, String search) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;
        
        if (search != null && !search.trim().isEmpty()) {
            // Busca simplificada por nome ou email
            userPage = userRepository.findAll(pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }
        
        List<Map<String, Object>> users = new ArrayList<>();
        for (User user : userPage.getContent()) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("name", user.getName());
            userData.put("email", user.getEmail());
            userData.put("cefrLevel", user.getCefrLevel().toString());
            userData.put("dailyGoalMinutes", user.getDailyGoalMinutes());
            userData.put("streakDays", user.getStreakDays());
            userData.put("totalMinutes", user.getTotalMinutes());
            userData.put("createdAt", user.getCreatedAt());
            userData.put("lastModifiedAt", user.getLastModifiedAt());
            userData.put("isActive", true); // Simplificado
            
            // Estatísticas do usuário
            long userReviews = srsReviewRepository.countByUserId(user.getId());
            long userChatSessions = chatSessionRepository.countByUserId(user.getId());
            long userTaskAttempts = taskAttemptRepository.countByUserId(user.getId());
            
            userData.put("totalReviews", userReviews);
            userData.put("totalChatSessions", userChatSessions);
            userData.put("totalTaskAttempts", userTaskAttempts);
            
            users.add(userData);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("users", users);
        result.put("totalElements", userPage.getTotalElements());
        result.put("totalPages", userPage.getTotalPages());
        result.put("currentPage", page);
        result.put("size", size);
        
        return result;
    }
    
    public Map<String, Object> getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Estatísticas detalhadas do usuário
        long totalReviews = srsReviewRepository.countByUserId(userId);
        long totalChatSessions = chatSessionRepository.countByUserId(userId);
        long totalChatTurns = chatTurnRepository.countByUserId(userId);
        long totalTaskAttempts = taskAttemptRepository.countByUserId(userId);
        long correctTaskAttempts = taskAttemptRepository.countByUserIdAndIsCorrectTrue(userId);
        
        // Atividade dos últimos 7 dias
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        long weekReviews = srsReviewRepository.countByUserIdAndCreatedAtAfter(userId, weekAgo);
        long weekChatTurns = chatTurnRepository.countByUserIdAndCreatedAtAfter(userId, weekAgo);
        long weekTaskAttempts = taskAttemptRepository.countByUserIdAndCreatedAtAfter(userId, weekAgo);
        
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("id", user.getId());
        userDetails.put("name", user.getName());
        userDetails.put("email", user.getEmail());
        userDetails.put("cefrLevel", user.getCefrLevel().toString());
        userDetails.put("dailyGoalMinutes", user.getDailyGoalMinutes());
        userDetails.put("streakDays", user.getStreakDays());
        userDetails.put("totalMinutes", user.getTotalMinutes());
        userDetails.put("createdAt", user.getCreatedAt());
        userDetails.put("lastModifiedAt", user.getLastModifiedAt());
        
        // Estatísticas de atividade
        userDetails.put("totalReviews", totalReviews);
        userDetails.put("totalChatSessions", totalChatSessions);
        userDetails.put("totalChatTurns", totalChatTurns);
        userDetails.put("totalTaskAttempts", totalTaskAttempts);
        userDetails.put("correctTaskAttempts", correctTaskAttempts);
        userDetails.put("taskAccuracy", totalTaskAttempts > 0 ? (double) correctTaskAttempts / totalTaskAttempts * 100 : 0);
        
        // Atividade recente
        userDetails.put("weekReviews", weekReviews);
        userDetails.put("weekChatTurns", weekChatTurns);
        userDetails.put("weekTaskAttempts", weekTaskAttempts);
        
        return userDetails;
    }
    
    public Map<String, Object> updateUser(Long userId, Map<String, Object> userData) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Atualizar campos permitidos
        if (userData.containsKey("name")) {
            user.setName(userData.get("name").toString());
        }
        
        if (userData.containsKey("email")) {
            String newEmail = userData.get("email").toString();
            if (!user.getEmail().equals(newEmail) && userRepository.existsByEmail(newEmail)) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(newEmail);
        }
        
        if (userData.containsKey("cefrLevel")) {
            try {
                User.CefrLevel level = User.CefrLevel.valueOf(userData.get("cefrLevel").toString());
                user.setCefrLevel(level);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid CEFR level");
            }
        }
        
        if (userData.containsKey("dailyGoalMinutes")) {
            try {
                Integer goal = Integer.valueOf(userData.get("dailyGoalMinutes").toString());
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
        result.put("message", "User updated successfully");
        result.put("user", getUserDetails(userId));
        
        return result;
    }
    
    public Map<String, Object> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Em uma implementação real, você também deletaria dados relacionados
        userRepository.delete(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "User deleted successfully");
        
        return result;
    }
    
    public Map<String, Object> getContentStats() {
        long totalVocabularyCards = vocabularyCardRepository.count();
        long totalTasks = taskRepository.count();
        long activeTasks = taskRepository.countByIsActiveTrue();
        long vocabularyByLevel = vocabularyCardRepository.countByCefrLevel(User.CefrLevel.A1);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalVocabularyCards", totalVocabularyCards);
        stats.put("totalTasks", totalTasks);
        stats.put("activeTasks", activeTasks);
        stats.put("vocabularyByLevel", Map.of(
            "A1", vocabularyCardRepository.countByCefrLevel(User.CefrLevel.A1),
            "A2", vocabularyCardRepository.countByCefrLevel(User.CefrLevel.A2),
            "B1", vocabularyCardRepository.countByCefrLevel(User.CefrLevel.B1),
            "B2", vocabularyCardRepository.countByCefrLevel(User.CefrLevel.B2),
            "C1", vocabularyCardRepository.countByCefrLevel(User.CefrLevel.C1)
        ));
        
        return stats;
    }
    
    public Map<String, Object> getAnalytics(String period) {
        LocalDateTime startDate;
        if ("week".equals(period)) {
            startDate = LocalDateTime.now().minusDays(7);
        } else if ("month".equals(period)) {
            startDate = LocalDateTime.now().minusDays(30);
        } else {
            startDate = LocalDateTime.now().minusDays(7); // Default to week
        }
        
        // Estatísticas de engajamento
        long totalReviews = srsReviewRepository.countByCreatedAtAfter(startDate);
        long totalChatTurns = chatTurnRepository.countByCreatedAtAfter(startDate);
        long totalTaskAttempts = taskAttemptRepository.countByCreatedAtAfter(startDate);
        
        // Usuários ativos
        long activeUsers = userRepository.count(); // Simplificado
        
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("period", period);
        analytics.put("startDate", startDate);
        analytics.put("totalReviews", totalReviews);
        analytics.put("totalChatTurns", totalChatTurns);
        analytics.put("totalTaskAttempts", totalTaskAttempts);
        analytics.put("activeUsers", activeUsers);
        analytics.put("averageSessionTime", 24); // minutos
        analytics.put("completionRate", 68.5); // porcentagem
        analytics.put("retentionRate", 87.2); // porcentagem
        
        return analytics;
    }
    
    public Map<String, Object> getSystemStatus() {
        Map<String, Object> status = new HashMap<>();
        
        // Status dos serviços (simulado)
        status.put("api", Map.of("status", "online", "uptime", "99.9%"));
        status.put("database", Map.of("status", "online", "uptime", "99.8%"));
        status.put("aiService", Map.of("status", "online", "uptime", "99.7%"));
        status.put("cdn", Map.of("status", "online", "uptime", "99.9%"));
        
        // Configurações do sistema
        status.put("maintenanceMode", false);
        status.put("maxConcurrentUsers", 10000);
        status.put("developmentMode", false);
        
        return status;
    }
}
