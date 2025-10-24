package com.ailinguo.facade;

import com.ailinguo.dto.UserDto;
import com.ailinguo.dto.auth.AuthResponse;
import com.ailinguo.dto.auth.LoginRequest;
import com.ailinguo.service.*; // Importe todos os seus serviços
import com.ailinguo.controller.VocabularyController; // Temporário, idealmente usar VocabularyService
import com.ailinguo.controller.ExerciseController;   // Temporário, idealmente usar ExerciseService
import com.ailinguo.model.User; // Importe o modelo User se necessário para DTOs ou setup
import com.ailinguo.model.UserVocabularyProgress; // Importe se usado em retornos mockados
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity; // Necessário para mockar VocabularyController
import org.springframework.security.core.Authentication;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Importações estáticas para facilitar a leitura dos asserts e mocks
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq; // Para verificações exatas de argumentos
import static org.mockito.Mockito.*;

// Habilita o uso das anotações do Mockito (@Mock, @InjectMocks) com JUnit 5
@ExtendWith(MockitoExtension.class)
class AILinguoFacadeTest {

    // --- Mocks para TODAS as dependências da AILinguoFacade ---
    @Mock private AuthService authService;
    @Mock private UserSettingsService userSettingsService;
    @Mock private TaskService taskService;
    @Mock private ExerciseController exerciseController; // Mock do controller (idealmente seria Service)
    @Mock private VocabularyController vocabularyController; // Mock do controller (idealmente seria Service)
    @Mock private DashboardService dashboardService;
    @Mock private GamificationService gamificationService;
    // XpService não é injetado diretamente na Facade neste exemplo

    // Mocks para objetos HttpServletResponse e Authentication
    @Mock private HttpServletResponse httpServletResponse;
    @Mock private Authentication authentication;

    // --- A classe que estamos testando ---
    // Injeta os mocks declarados acima na instância da Facade
    @InjectMocks
    private AILinguoFacade aiLinguoFacade;

    // Campos para guardar dados de teste reutilizáveis
    private Map<String, Object> registrationRequest;
    private LoginRequest loginRequest;
    private UserDto sampleUserDto;
    private AuthResponse sampleAuthResponse;
    private final Long sampleUserId = 1L;

    // Configura dados de teste comuns antes de cada teste
    @BeforeEach
    void setUp() {
        registrationRequest = new HashMap<>();
        registrationRequest.put("email", "test@example.com");
        registrationRequest.put("name", "Test User");
        registrationRequest.put("password", "password123");
        registrationRequest.put("cefrLevel", "A2");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        sampleUserDto = UserDto.builder()
                .id(sampleUserId.toString()) // ID como String, conforme seu DTO
                .name("Test User")
                .email("test@example.com")
                .cefrLevel(User.CefrLevel.A2) // Adicionando nível para consistência
                .build();

        sampleAuthResponse = AuthResponse.builder().user(sampleUserDto).build();
    }

    // --- Testes de Autenticação ---

    @Test
    @DisplayName("registerUser: Deve chamar AuthService.register e retornar AuthResponse")
    void registerUser_shouldCallAuthServiceRegister_andReturnAuthResponse() {
        // Arrange
        when(authService.register(any(Map.class), any(HttpServletResponse.class)))
                .thenReturn(sampleAuthResponse);

        // Act
        AuthResponse actualResponse = aiLinguoFacade.registerUser(registrationRequest, httpServletResponse);

        // Assert
        assertNotNull(actualResponse, "A resposta não deveria ser nula.");
        assertEquals(sampleAuthResponse, actualResponse, "A resposta retornada deve ser a mesma que o mock retornou.");
        assertEquals("Test User", actualResponse.getUser().getName());

        verify(authService, times(1)).register(eq(registrationRequest), eq(httpServletResponse));
        verifyNoInteractions(userSettingsService, taskService, dashboardService, gamificationService);
        verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("registerUser: Deve propagar exceção se AuthService falhar")
    void registerUser_shouldPropagateException_whenAuthServiceThrowsException() {
        // Arrange
        String errorMessage = "Email already exists";
        when(authService.register(any(Map.class), any(HttpServletResponse.class)))
                .thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            aiLinguoFacade.registerUser(registrationRequest, httpServletResponse);
        });
        assertEquals(errorMessage, exception.getMessage());

        verify(authService, times(1)).register(any(Map.class), any(HttpServletResponse.class));
        verifyNoMoreInteractions(authService);
        verifyNoInteractions(userSettingsService, taskService);
    }

    @Test
    @DisplayName("loginUser: Deve chamar AuthService.login e retornar AuthResponse")
    void loginUser_shouldCallAuthServiceLogin_andReturnAuthResponse() {
        // Arrange
        when(authService.login(any(LoginRequest.class), any(HttpServletResponse.class)))
                .thenReturn(sampleAuthResponse);

        // Act
        AuthResponse result = aiLinguoFacade.loginUser(loginRequest, httpServletResponse);

        // Assert
        assertNotNull(result);
        assertEquals(sampleUserDto, result.getUser());
        verify(authService, times(1)).login(loginRequest, httpServletResponse);
        verifyNoMoreInteractions(authService);
    }

    @Test
    @DisplayName("getCurrentAuthenticatedUser: Deve chamar AuthService.getCurrentUser com ID correto")
    void getCurrentAuthenticatedUser_shouldCallAuthService() {
        // Arrange
        String userIdString = sampleUserId.toString();
        when(authentication.getPrincipal()).thenReturn(userIdString); // Simula um usuário autenticado
        when(authService.getCurrentUser(userIdString)).thenReturn(sampleUserDto);

        // Act
        UserDto result = aiLinguoFacade.getCurrentAuthenticatedUser(authentication);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.getName());
        verify(authService, times(1)).getCurrentUser(userIdString);
    }

    @Test
    @DisplayName("getCurrentAuthenticatedUser: Deve retornar null se não autenticado")
    void getCurrentAuthenticatedUser_shouldReturnNull_whenNotAuthenticated() {
        // Arrange
        when(authentication.getPrincipal()).thenReturn(null); // Simula não autenticado
        when(authService.getCurrentUser(null)).thenReturn(null); // Configura o mock para retornar null

        // Act
        UserDto result = aiLinguoFacade.getCurrentAuthenticatedUser(authentication);

        // Assert
        assertNull(result);
        verify(authService, times(1)).getCurrentUser(null);
    }


    @Test
    @DisplayName("logoutUser: Deve chamar AuthService.logout")
    void logoutUser_shouldCallAuthServiceLogout() {
        // Arrange
        doNothing().when(authService).logout(any(HttpServletResponse.class)); // Configura mock para método void

        // Act
        aiLinguoFacade.logoutUser(httpServletResponse);

        // Assert
        verify(authService, times(1)).logout(httpServletResponse);
    }

    // --- Testes de Perfil ---

    @Test
    @DisplayName("getUserProfile: Deve chamar UserSettingsService.getUserProfile")
    void getUserProfile_shouldCallUserSettingsService() {
        // Arrange
        Map<String, Object> profileMap = Map.of("id", sampleUserId, "name", "Test User", "email", "test@example.com");
        when(userSettingsService.getUserProfile(sampleUserId)).thenReturn(profileMap);

        // Act
        Map<String, Object> result = aiLinguoFacade.getUserProfile(sampleUserId);

        // Assert
        assertNotNull(result);
        assertEquals("Test User", result.get("name"));
        verify(userSettingsService, times(1)).getUserProfile(sampleUserId);
    }

    @Test
    @DisplayName("updateUserProfile: Deve chamar UserSettingsService.updateUserProfile")
    void updateUserProfile_shouldCallUserSettingsService() {
        // Arrange
        Map<String, Object> profileUpdateData = Map.of("name", "Updated Name");
        Map<String, Object> updatedProfileMap = Map.of("id", sampleUserId, "name", "Updated Name", "email", "test@example.com");
        when(userSettingsService.updateUserProfile(sampleUserId, profileUpdateData)).thenReturn(updatedProfileMap);

        // Act
        Map<String, Object> result = aiLinguoFacade.updateUserProfile(sampleUserId, profileUpdateData);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.get("name"));
        verify(userSettingsService, times(1)).updateUserProfile(sampleUserId, profileUpdateData);
    }

     @Test
    @DisplayName("changeUserPassword: Deve chamar UserSettingsService.changePassword")
    void changeUserPassword_shouldCallUserSettingsService() {
        // Arrange
        String currentPassword = "oldPassword";
        String newPassword = "newPasswordStrong";
        Map<String, Object> successResponse = Map.of("success", true);
        when(userSettingsService.changePassword(sampleUserId, currentPassword, newPassword)).thenReturn(successResponse);

        // Act
        Map<String, Object> result = aiLinguoFacade.changeUserPassword(sampleUserId, currentPassword, newPassword);

        // Assert
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        verify(userSettingsService, times(1)).changePassword(sampleUserId, currentPassword, newPassword);
    }


    // --- Testes de Conteúdo/Exercícios ---

    @Test
    @DisplayName("getRecommendedTasks: Deve chamar TaskService.getRandomTasks")
    void getRecommendedTasks_shouldCallTaskService() {
        // Arrange
        int limit = 5;
        Map<String, Object> tasksMap = Map.of("tasks", List.of(Map.of("id", 1L, "title", "Task 1")));
        when(taskService.getRandomTasks(sampleUserId, limit, null, null)).thenReturn(tasksMap);

        // Act
        Map<String, Object> result = aiLinguoFacade.getRecommendedTasks(sampleUserId, limit);

        // Assert
        assertNotNull(result);
        assertTrue(result.containsKey("tasks"));
        verify(taskService, times(1)).getRandomTasks(sampleUserId, limit, null, null);
    }

    @Test
    @DisplayName("submitTaskAttempt: Deve chamar TaskService.submitTaskAttempt")
    void submitTaskAttempt_shouldCallTaskService() {
        // Arrange
        Long taskId = 10L;
        Integer selectedAnswerIndex = 1;
        Integer timeSpent = 30;
        Map<String, Object> attemptResult = Map.of("isCorrect", true, "xpEarned", 15);
        when(taskService.submitTaskAttempt(sampleUserId, taskId, selectedAnswerIndex, timeSpent)).thenReturn(attemptResult);

        // Act
        Map<String, Object> result = aiLinguoFacade.submitTaskAttempt(sampleUserId, taskId, selectedAnswerIndex, timeSpent);

        // Assert
        assertNotNull(result);
        assertTrue((Boolean) result.get("isCorrect"));
        assertEquals(15, result.get("xpEarned"));
        verify(taskService, times(1)).submitTaskAttempt(sampleUserId, taskId, selectedAnswerIndex, timeSpent);
    }

    @Test
    @DisplayName("getTaskAndVocabularySummary: Deve chamar TaskService e VocabularyController")
    void getTaskAndVocabularySummary_shouldCallServices() {
        // Arrange
        Map<String, Object> taskStats = Map.of("totalAttempts", 5, "accuracy", 60.0);
        // Simula o retorno do VocabularyController (ResponseEntity)
        ResponseEntity<List<UserVocabularyProgress>> vocabResponseEntity = ResponseEntity.ok(Collections.emptyList());
        List<UserVocabularyProgress> vocabProgressBody = Collections.emptyList(); // O corpo da ResponseEntity

        when(taskService.getUserTaskStats(sampleUserId)).thenReturn(taskStats);
        when(vocabularyController.getUserProgress(sampleUserId)).thenReturn(vocabResponseEntity);

        // Act
        Map<String, Object> summary = aiLinguoFacade.getTaskAndVocabularySummary(sampleUserId);

        // Assert
        assertNotNull(summary);
        assertTrue((Boolean) summary.get("success"));
        assertEquals(taskStats, summary.get("taskStats"));
        // Verifica se o corpo da resposta do controller foi colocado no sumário
        assertEquals(vocabProgressBody, summary.get("vocabularyProgress"));

        verify(taskService, times(1)).getUserTaskStats(sampleUserId);
        verify(vocabularyController, times(1)).getUserProgress(sampleUserId);
    }

    // --- Testes de Progresso ---

    @Test
    @DisplayName("getUserOverallProgressSummary: Deve chamar serviços de progresso e combinar")
    void getUserOverallProgressSummary_shouldCallAllRelevantServicesAndCombineResults() {
        // Arrange
        Map<String, Object> dashboardStats = Map.of("totalXp", 100, "level", 2);
        Map<String, Object> ranking = Map.of("rank", 5, "totalUsers", 50);
        Map<String, Object> goals = Map.of("completedGoals", 1, "totalGoals", 3);
        Map<String, Object> taskStats = Map.of("totalAttempts", 10, "accuracy", 80.0);

        when(dashboardService.getUserStats(sampleUserId)).thenReturn(dashboardStats);
        when(gamificationService.getUserRanking(sampleUserId)).thenReturn(ranking);
        when(gamificationService.getUserGoals(sampleUserId)).thenReturn(goals);
        when(taskService.getUserTaskStats(sampleUserId)).thenReturn(taskStats);

        // Act
        Map<String, Object> summary = aiLinguoFacade.getUserOverallProgressSummary(sampleUserId);

        // Assert
        assertNotNull(summary);
        assertTrue((Boolean) summary.get("success"));
        assertEquals(dashboardStats, summary.get("dashboardStats"));
        assertEquals(ranking, summary.get("ranking"));
        assertEquals(goals, summary.get("goals"));
        assertEquals(taskStats, summary.get("taskStats"));

        verify(dashboardService, times(1)).getUserStats(sampleUserId);
        verify(gamificationService, times(1)).getUserRanking(sampleUserId);
        verify(gamificationService, times(1)).getUserGoals(sampleUserId);
        verify(taskService, times(1)).getUserTaskStats(sampleUserId);
        verifyNoInteractions(authService, userSettingsService);
    }

    @Test
    @DisplayName("getUserOverallProgressSummary: Deve tratar erro em um dos serviços")
    void getUserOverallProgressSummary_shouldHandleServiceError() {
        // Arrange: Simula um erro ao buscar o ranking
        Map<String, Object> dashboardStats = Map.of("totalXp", 100, "level", 2);
        when(dashboardService.getUserStats(sampleUserId)).thenReturn(dashboardStats);
        when(gamificationService.getUserRanking(sampleUserId)).thenThrow(new RuntimeException("Ranking service unavailable"));

        // Act
        Map<String, Object> summary = aiLinguoFacade.getUserOverallProgressSummary(sampleUserId);

        // Assert
        assertNotNull(summary);
        assertFalse((Boolean) summary.get("success"));
        assertTrue(((String) summary.get("error")).contains("Ranking service unavailable"));

        // Verifica que os serviços até o ponto do erro foram chamados
        verify(dashboardService, times(1)).getUserStats(sampleUserId);
        verify(gamificationService, times(1)).getUserRanking(sampleUserId);
        // Garante que os serviços *depois* do erro não foram chamados
        verifyNoInteractions(taskService);
    }

    @Test
    @DisplayName("getUserActivityHistory: Deve chamar TaskService.getUserTaskHistory")
    void getUserActivityHistory_shouldCallTaskService() {
        // Arrange
        Map<String, Object> historyMap = Map.of("recentAttempts", List.of(Map.of("id", 1L)));
        when(taskService.getUserTaskHistory(sampleUserId)).thenReturn(historyMap);

        // Act
        Map<String, Object> result = aiLinguoFacade.getUserActivityHistory(sampleUserId);

        // Assert
        assertNotNull(result);
        assertTrue((Boolean) result.get("success"));
        assertEquals(historyMap, result.get("taskHistory"));
        verify(taskService, times(1)).getUserTaskHistory(sampleUserId);
        // Adicionar verificações para histórico de vocabulário/chat se implementado
    }

    @Test
    @DisplayName("getUserAchievements: Deve chamar DashboardService.getUserAchievements")
    void getUserAchievements_shouldCallDashboardService() {
        // Arrange
        Map<String, Object> achievementsMap = Map.of("achievements", List.of(Map.of("title", "First Step")));
        when(dashboardService.getUserAchievements(sampleUserId)).thenReturn(achievementsMap);

        // Act
        Map<String, Object> result = aiLinguoFacade.getUserAchievements(sampleUserId);

        // Assert
        assertNotNull(result);
        // Verifica se o resultado é o mesmo mapa retornado pelo serviço
        assertEquals(achievementsMap, result);
        verify(dashboardService, times(1)).getUserAchievements(sampleUserId);
    }
}