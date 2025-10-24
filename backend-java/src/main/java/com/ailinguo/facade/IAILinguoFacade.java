package com.ailinguo.facade;

import java.util.HashMap;

import com.ailinguo.dto.UserDto;
import com.ailinguo.dto.auth.AuthResponse;
import com.ailinguo.dto.auth.LoginRequest;
import com.ailinguo.controller.ExerciseController;
import com.ailinguo.controller.VocabularyController;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import com.ailinguo.service.AuthService;
import com.ailinguo.service.DashboardService;
import com.ailinguo.service.GamificationService;
import com.ailinguo.service.TaskService;
import com.ailinguo.service.UserSettingsService;

public interface IAILinguoFacade {

    // --- Autenticação ---
    AuthResponse registerUser(Map<String, Object> requestData, HttpServletResponse response);

    AuthResponse loginUser(LoginRequest loginRequest, HttpServletResponse response);

    UserDto getCurrentAuthenticatedUser(Authentication authentication);

    void logoutUser(HttpServletResponse response);

    // --- Perfil ---
    Map<String, Object> getUserProfile(Long userId);

    Map<String, Object> updateUserProfile(Long userId, Map<String, Object> profileData);

    Map<String, Object> getUserPreferences(Long userId);

    Map<String, Object> updateUserPreferences(Long userId, Map<String, Object> preferencesData);

    Map<String, Object> changeUserPassword(Long userId, String currentPassword, String newPassword);

    // --- Aulas / Exercícios / Vocabulário ---
    Map<String, Object> getRecommendedTasks(Long userId, int limit);

    Map<String, Object> submitTaskAttempt(Long userId, Long taskId, Integer selectedAnswerIndex, Integer timeSpent);

    Map<String, Object> getTaskAndVocabularySummary(Long userId); // Exemplo combinado

    // --- Progresso ---
    Map<String, Object> getUserOverallProgressSummary(Long userId); // Usará DashboardService, GamificationService, etc.

    Map<String, Object> getUserActivityHistory(Long userId); // Combina histórico de tarefas, vocabulário, etc.

    Map<String, Object> getUserAchievements(Long userId); // Poderia vir do DashboardService ou GamificationService
}

class AILinguoFacade implements IAILinguoFacade {

    private static final Logger logger = LoggerFactory.getLogger(AILinguoFacade.class);

    // Injeção de todos os serviços relevantes
    private final AuthService authService; //
    private final UserSettingsService userSettingsService; //
    private final TaskService taskService; //
    private final ExerciseController exerciseController; // Idealmente seria um ExerciseService
    private final VocabularyController vocabularyController; // Idealmente seria um VocabularyService
    private final DashboardService dashboardService; //
    private final GamificationService gamificationService; //
    // private final XpService xpService; // XpService é provavelmente usado internamente pelos outros serviços

    public AILinguoFacade(AuthService authService,
            UserSettingsService userSettingsService,
            TaskService taskService,
            ExerciseController exerciseController,
            VocabularyController vocabularyController,
            DashboardService dashboardService,
            GamificationService gamificationService
    /*XpService xpService*/) { //
        this.authService = authService;
        this.userSettingsService = userSettingsService;
        this.taskService = taskService;
        this.exerciseController = exerciseController;
        this.vocabularyController = vocabularyController;
        this.dashboardService = dashboardService;
        this.gamificationService = gamificationService;
        // this.xpService = xpService;
    }

    // --- Autenticação ---
    @Override
    public AuthResponse registerUser(Map<String, Object> requestData, HttpServletResponse response) {
        logger.info("Facade: Registrando usuário...");
        return authService.register(requestData, response); //
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest, HttpServletResponse response) {
        logger.info("Facade: Realizando login para {}", loginRequest.getEmail()); //
        return authService.login(loginRequest, response); //
    }

    @Override
    public UserDto getCurrentAuthenticatedUser(Authentication authentication) {
        logger.debug("Facade: Buscando usuário autenticado...");
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;
        return authService.getCurrentUser(userId); //
    }

    @Override
    public void logoutUser(HttpServletResponse response) {
        logger.info("Facade: Realizando logout...");
        authService.logout(response); //
    }

    // --- Perfil ---
    @Override
    public Map<String, Object> getUserProfile(Long userId) {
        logger.info("Facade: Buscando perfil para usuário ID: {}", userId);
        return userSettingsService.getUserProfile(userId); //
    }

    @Override
    public Map<String, Object> updateUserProfile(Long userId, Map<String, Object> profileData) {
        logger.info("Facade: Atualizando perfil para usuário ID: {}", userId);
        return userSettingsService.updateUserProfile(userId, profileData); //
    }

    @Override
    public Map<String, Object> getUserPreferences(Long userId) {
        logger.info("Facade: Buscando preferências para usuário ID: {}", userId);
        return userSettingsService.getUserPreferences(userId); //
    }

    @Override
    public Map<String, Object> updateUserPreferences(Long userId, Map<String, Object> preferencesData) {
        logger.info("Facade: Atualizando preferências para usuário ID: {}", userId);
        return userSettingsService.updateUserPreferences(userId, preferencesData); //
    }

    @Override
    public Map<String, Object> changeUserPassword(Long userId, String currentPassword, String newPassword) {
        logger.info("Facade: Alterando senha para usuário ID: {}", userId);
        return userSettingsService.changePassword(userId, currentPassword, newPassword); //
    }

    // --- Aulas / Exercícios / Vocabulário ---
    @Override
    public Map<String, Object> getRecommendedTasks(Long userId, int limit) {
        logger.info("Facade: Buscando tarefas recomendadas para usuário ID: {}", userId);
        // Pode adicionar lógica mais complexa aqui (baseada em histórico, etc.)
        return taskService.getRandomTasks(userId, limit, null, null); //
    }

    @Override
    public Map<String, Object> submitTaskAttempt(Long userId, Long taskId, Integer selectedAnswerIndex, Integer timeSpent) {
        logger.info("Facade: Submetendo tentativa de tarefa ID: {} para usuário ID: {}", taskId, userId);
        // A lógica de adicionar XP já deve estar no TaskService
        return taskService.submitTaskAttempt(userId, taskId, selectedAnswerIndex, timeSpent); //
    }

    @Override
    public Map<String, Object> getTaskAndVocabularySummary(Long userId) {
        logger.info("Facade: Buscando resumo de tarefas e vocabulário para usuário ID: {}", userId);
        Map<String, Object> summary = new HashMap<>();
        try {
            summary.put("taskStats", taskService.getUserTaskStats(userId)); //
            // Supondo que VocabularyController tem um método para progresso
            summary.put("vocabularyProgress", vocabularyController.getUserProgress(userId).getBody()); //
            summary.put("success", true);
        } catch (Exception e) {
            logger.error("Erro ao buscar resumo de atividades para {}: {}", userId, e.getMessage());
            summary.put("success", false);
            summary.put("error", "Erro ao buscar resumo de atividades.");
        }
        return summary;
    }

    // --- Progresso ---
    @Override
    public Map<String, Object> getUserOverallProgressSummary(Long userId) {
        logger.info("Facade: Buscando resumo geral de progresso para usuário ID: {}", userId);
        Map<String, Object> summary = new HashMap<>();
        try {
            summary.put("dashboardStats", dashboardService.getUserStats(userId)); //
            summary.put("ranking", gamificationService.getUserRanking(userId)); //
            summary.put("goals", gamificationService.getUserGoals(userId)); //
            summary.put("taskStats", taskService.getUserTaskStats(userId)); //
            summary.put("success", true);
        } catch (Exception e) {
            logger.error("Erro ao buscar resumo geral para {}: {}", userId, e.getMessage());
            summary.put("success", false);
            summary.put("error", "Erro ao buscar resumo geral.");
        }
        return summary;
    }

    @Override
    public Map<String, Object> getUserActivityHistory(Long userId) {
        logger.info("Facade: Buscando histórico de atividades para usuário ID: {}", userId);
        Map<String, Object> history = new HashMap<>();
        try {
            history.put("taskHistory", taskService.getUserTaskHistory(userId)); //
            // Adicionar histórico de vocabulário, chat, etc. se necessário
            // history.put("vocabularyHistory", vocabularyController.getUserProgress(userId).getBody()); // Exemplo
            history.put("success", true);
        } catch (Exception e) {
            logger.error("Erro ao buscar histórico para {}: {}", userId, e.getMessage());
            history.put("success", false);
            history.put("error", "Erro ao buscar histórico.");
        }
        return history;
    }

    @Override
    public Map<String, Object> getUserAchievements(Long userId) {
        logger.info("Facade: Buscando conquistas para usuário ID: {}", userId);
        // Supondo que DashboardService tem o método
        try {
            return dashboardService.getUserAchievements(userId); //
        } catch (Exception e) {
            logger.error("Erro ao buscar conquistas para {}: {}", userId, e.getMessage());
            return Map.of("success", false, "error", "Erro ao buscar conquistas.");
        }
    }
}
