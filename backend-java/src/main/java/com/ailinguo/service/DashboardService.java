package com.ailinguo.service;

import com.ailinguo.model.User;
import com.ailinguo.model.VocabularyCard;
import com.ailinguo.model.SrsReview;
import com.ailinguo.model.ChatSession;
import com.ailinguo.model.ChatTurn;
import com.ailinguo.repository.UserRepository;
import com.ailinguo.repository.VocabularyCardRepository;
import com.ailinguo.repository.SrsReviewRepository;
import com.ailinguo.repository.ChatSessionRepository;
import com.ailinguo.repository.ChatTurnRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DashboardService {
    
    private final UserRepository userRepository;
    private final VocabularyCardRepository vocabularyCardRepository;
    private final SrsReviewRepository srsReviewRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatTurnRepository chatTurnRepository;
    
    public DashboardService(UserRepository userRepository, 
                           VocabularyCardRepository vocabularyCardRepository,
                           SrsReviewRepository srsReviewRepository,
                           ChatSessionRepository chatSessionRepository,
                           ChatTurnRepository chatTurnRepository) {
        this.userRepository = userRepository;
        this.vocabularyCardRepository = vocabularyCardRepository;
        this.srsReviewRepository = srsReviewRepository;
        this.chatSessionRepository = chatSessionRepository;
        this.chatTurnRepository = chatTurnRepository;
    }
    
    public Map<String, Object> getUserStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Calcular estatísticas
        long totalVocabularyCards = vocabularyCardRepository.count();
        long userReviews = srsReviewRepository.countByUserId(userId);
        long chatSessions = chatSessionRepository.countByUserId(userId);
        long chatTurns = chatTurnRepository.countByUserId(userId);
        
        // Use real XP from database
        Integer totalXp = user.getTotalXp() != null ? user.getTotalXp() : 0;
        Integer level = user.getLevel() != null ? user.getLevel() : 1;
        
        // Calcular streak (dias consecutivos com atividade)
        long streak = calculateStreak(userId);
        
        // Calcular progresso diário
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        long todayReviews = srsReviewRepository.countByUserIdAndCreatedAtAfter(userId, today);
        long todayChatTurns = chatTurnRepository.countByUserIdAndCreatedAtAfter(userId, today);
        long todayMinutes = (todayReviews * 2) + (todayChatTurns * 3); // Estimativa de tempo
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("streakDays", streak);
        stats.put("totalXp", totalXp);
        stats.put("level", level);
        stats.put("dailyGoalMinutes", user.getDailyGoalMinutes() != null ? user.getDailyGoalMinutes() : 15);
        stats.put("todayMinutes", todayMinutes);
        stats.put("cefrLevel", user.getCefrLevel() != null ? user.getCefrLevel().toString() : "A1");
        stats.put("totalReviews", userReviews);
        stats.put("totalChatSessions", chatSessions);
        stats.put("totalVocabularyCards", totalVocabularyCards);
        stats.put("progressPercentage", Math.min(100, (todayMinutes * 100) / (user.getDailyGoalMinutes() != null ? user.getDailyGoalMinutes() : 15)));
        
        return stats;
    }
    
    public Map<String, Object> getUserProgress(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Progresso dos últimos 7 dias
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        List<Map<String, Object>> weeklyProgress = new ArrayList<>();
        
        for (int i = 6; i >= 0; i--) {
            LocalDateTime day = LocalDateTime.now().minusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime nextDay = day.plusDays(1);
            
            long dayReviews = srsReviewRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            long dayChatTurns = chatTurnRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            long dayMinutes = (dayReviews * 2) + (dayChatTurns * 3);
            
            Map<String, Object> dayProgress = new HashMap<>();
            dayProgress.put("date", day.toLocalDate().toString());
            dayProgress.put("reviews", dayReviews);
            dayProgress.put("chatTurns", dayChatTurns);
            dayProgress.put("minutes", dayMinutes);
            dayProgress.put("goalReached", dayMinutes >= user.getDailyGoalMinutes());
            
            weeklyProgress.add(dayProgress);
        }
        
        Map<String, Object> progress = new HashMap<>();
        progress.put("weeklyProgress", weeklyProgress);
        progress.put("currentStreak", calculateStreak(userId));
        progress.put("longestStreak", calculateLongestStreak(userId));
        progress.put("totalStudyTime", calculateTotalStudyTime(userId));
        
        return progress;
    }
    
    public Map<String, Object> getUserAchievements(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Map<String, Object>> achievements = new ArrayList<>();
        
        // Verificar conquistas
        long totalReviews = srsReviewRepository.countByUserId(userId);
        long totalChatTurns = chatTurnRepository.countByUserId(userId);
        long streak = calculateStreak(userId);
        
        // Conquista: Primeira revisão
        if (totalReviews >= 1) {
            achievements.add(createAchievement("Primeira Revisão", "Você completou sua primeira revisão de vocabulário!", "first_review", true));
        }
        
        // Conquista: 10 revisões
        if (totalReviews >= 10) {
            achievements.add(createAchievement("10 Revisões", "Você completou 10 revisões de vocabulário!", "ten_reviews", true));
        }
        
        // Conquista: 100 revisões
        if (totalReviews >= 100) {
            achievements.add(createAchievement("100 Revisões", "Você completou 100 revisões de vocabulário!", "hundred_reviews", true));
        }
        
        // Conquista: Streak de 7 dias
        if (streak >= 7) {
            achievements.add(createAchievement("Streak de 7 dias", "Você estudou por 7 dias consecutivos!", "week_streak", true));
        }
        
        // Conquista: Streak de 30 dias
        if (streak >= 30) {
            achievements.add(createAchievement("Streak de 30 dias", "Você estudou por 30 dias consecutivos!", "month_streak", true));
        }
        
        // Conquista: Primeira conversa
        if (totalChatTurns >= 1) {
            achievements.add(createAchievement("Primeira Conversa", "Você teve sua primeira conversa com o tutor!", "first_chat", true));
        }
        
        // Conquista: 50 conversas
        if (totalChatTurns >= 50) {
            achievements.add(createAchievement("50 Conversas", "Você teve 50 conversas com o tutor!", "fifty_chats", true));
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("achievements", achievements);
        result.put("totalAchievements", achievements.size());
        result.put("recentAchievements", achievements.stream().limit(3).toList());
        
        return result;
    }
    
    private long calculateStreak(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        long streak = 0;
        
        for (int i = 0; i < 365; i++) {
            LocalDateTime day = now.minusDays(i).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime nextDay = day.plusDays(1);
            
            long dayReviews = srsReviewRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            long dayChatTurns = chatTurnRepository.countByUserIdAndCreatedAtBetween(userId, day, nextDay);
            
            if (dayReviews > 0 || dayChatTurns > 0) {
                streak++;
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    private long calculateLongestStreak(Long userId) {
        // Implementação simplificada - retorna o streak atual
        // Em uma implementação real, você salvaria o maior streak no banco
        return calculateStreak(userId);
    }
    
    private long calculateTotalStudyTime(Long userId) {
        long totalReviews = srsReviewRepository.countByUserId(userId);
        long totalChatTurns = chatTurnRepository.countByUserId(userId);
        
        // Estimativa: 2 minutos por revisão, 3 minutos por turno de chat
        return (totalReviews * 2) + (totalChatTurns * 3);
    }
    
    private Map<String, Object> createAchievement(String title, String description, String type, boolean unlocked) {
        Map<String, Object> achievement = new HashMap<>();
        achievement.put("title", title);
        achievement.put("description", description);
        achievement.put("type", type);
        achievement.put("unlocked", unlocked);
        achievement.put("unlockedAt", unlocked ? LocalDateTime.now().toString() : null);
        return achievement;
    }
}
