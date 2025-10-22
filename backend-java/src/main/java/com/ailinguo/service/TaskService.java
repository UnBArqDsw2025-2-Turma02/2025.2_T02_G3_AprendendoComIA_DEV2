package com.ailinguo.service;

import com.ailinguo.model.Task;
import com.ailinguo.model.TaskAttempt;
import com.ailinguo.model.TaskOption;
import com.ailinguo.model.User;
import com.ailinguo.repository.TaskRepository;
import com.ailinguo.repository.TaskAttemptRepository;
import com.ailinguo.repository.TaskOptionRepository;
import com.ailinguo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {
    
    private final TaskRepository taskRepository;
    private final TaskAttemptRepository taskAttemptRepository;
    private final TaskOptionRepository taskOptionRepository;
    private final UserRepository userRepository;
    
    public TaskService(TaskRepository taskRepository, 
                       TaskAttemptRepository taskAttemptRepository,
                       TaskOptionRepository taskOptionRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskAttemptRepository = taskAttemptRepository;
        this.taskOptionRepository = taskOptionRepository;
        this.userRepository = userRepository;
    }
    
    public Map<String, Object> getRandomTasks(Long userId, int limit, String difficulty, String type) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<Task> tasks;
        
        if (difficulty != null && type != null) {
            tasks = taskRepository.findByDifficultyAndTypeAndIsActiveTrue(difficulty, type);
        } else if (difficulty != null) {
            tasks = taskRepository.findByDifficultyAndIsActiveTrue(difficulty);
        } else if (type != null) {
            tasks = taskRepository.findByTypeAndIsActiveTrue(type);
        } else {
            // Buscar tarefas baseadas no nível do usuário
            String userLevel = user.getCefrLevel().toString();
            List<String> appropriateLevels = getAppropriateLevels(userLevel);
            tasks = taskRepository.findRandomTasksByDifficulties(appropriateLevels, limit);
        }
        
        // Limitar o número de tarefas
        if (tasks.size() > limit) {
            tasks = tasks.subList(0, limit);
        }
        
        // Converter para formato de resposta
        List<Map<String, Object>> taskList = new ArrayList<>();
        for (Task task : tasks) {
            // Buscar opções da tarefa
            List<TaskOption> options = taskOptionRepository.findByTaskIdOrderByOptionIndex(task.getId());
            List<String> optionTexts = options.stream()
                    .sorted(Comparator.comparing(TaskOption::getOptionIndex))
                    .map(TaskOption::getOptionText)
                    .toList();
            
            Map<String, Object> taskData = new HashMap<>();
            taskData.put("id", task.getId());
            taskData.put("title", task.getTitle());
            taskData.put("description", task.getDescription());
            taskData.put("type", task.getType());
            taskData.put("difficulty", task.getDifficulty());
            taskData.put("question", task.getQuestion());
            taskData.put("options", optionTexts);
            taskData.put("timeLimit", task.getTimeLimit());
            taskData.put("xpReward", task.getXpReward());
            taskList.add(taskData);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("tasks", taskList);
        result.put("totalTasks", taskList.size());
        result.put("userLevel", user.getCefrLevel().toString());
        
        return result;
    }
    
    public Map<String, Object> submitTaskAttempt(Long userId, Long taskId, Integer selectedAnswerIndex, Integer timeSpent) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        boolean isCorrect = selectedAnswerIndex.equals(task.getCorrectAnswerIndex());
        int xpEarned = isCorrect ? (task.getXpReward() != null ? task.getXpReward() : 10) : 2;
        
        // Criar tentativa
        TaskAttempt attempt = TaskAttempt.builder()
                .user(user)
                .task(task)
                .selectedAnswerIndex(selectedAnswerIndex)
                .isCorrect(isCorrect)
                .timeSpent(timeSpent)
                .xpEarned(xpEarned)
                .createdAt(LocalDateTime.now())
                .build();
        
        taskAttemptRepository.save(attempt);
        
        // Atualizar XP do usuário (simplificado)
        user.setTotalMinutes(user.getTotalMinutes() + (timeSpent != null ? timeSpent / 60 : 1));
        userRepository.save(user);
        
        Map<String, Object> result = new HashMap<>();
        result.put("isCorrect", isCorrect);
        result.put("correctAnswerIndex", task.getCorrectAnswerIndex());
        result.put("explanation", task.getExplanation());
        result.put("xpEarned", xpEarned);
        result.put("timeSpent", timeSpent);
        
        return result;
    }
    
    public Map<String, Object> getUserTaskStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        long totalAttempts = taskAttemptRepository.countByUserId(userId);
        long correctAttempts = taskAttemptRepository.countByUserIdAndIsCorrectTrue(userId);
        long todayAttempts = taskAttemptRepository.countByUserIdAndCreatedAtAfter(userId, 
                LocalDateTime.now().withHour(0).withMinute(0).withSecond(0));
        
        double accuracy = totalAttempts > 0 ? (double) correctAttempts / totalAttempts * 100 : 0;
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalAttempts", totalAttempts);
        stats.put("correctAttempts", correctAttempts);
        stats.put("todayAttempts", todayAttempts);
        stats.put("accuracy", Math.round(accuracy * 100.0) / 100.0);
        stats.put("userLevel", user.getCefrLevel().toString());
        
        return stats;
    }
    
    public Map<String, Object> getUserTaskHistory(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<TaskAttempt> recentAttempts = taskAttemptRepository.findByUserIdAndCreatedAtAfter(userId, 
                LocalDateTime.now().minusDays(7));
        
        List<Map<String, Object>> attempts = new ArrayList<>();
        for (TaskAttempt attempt : recentAttempts) {
            Map<String, Object> attemptData = new HashMap<>();
            attemptData.put("id", attempt.getId());
            attemptData.put("taskTitle", attempt.getTask().getTitle());
            attemptData.put("taskType", attempt.getTask().getType());
            attemptData.put("taskDifficulty", attempt.getTask().getDifficulty());
            attemptData.put("isCorrect", attempt.getIsCorrect());
            attemptData.put("timeSpent", attempt.getTimeSpent());
            attemptData.put("xpEarned", attempt.getXpEarned());
            attemptData.put("createdAt", attempt.getCreatedAt());
            attempts.add(attemptData);
        }
        
        Map<String, Object> result = new HashMap<>();
        result.put("recentAttempts", attempts);
        result.put("totalAttempts", attempts.size());
        
        return result;
    }
    
    private List<String> getAppropriateLevels(String userLevel) {
        List<String> levels = new ArrayList<>();
        levels.add(userLevel);
        
        // Adicionar níveis adjacentes
        switch (userLevel) {
            case "A1":
                levels.add("A2");
                break;
            case "A2":
                levels.add("A1");
                levels.add("B1");
                break;
            case "B1":
                levels.add("A2");
                levels.add("B2");
                break;
            case "B2":
                levels.add("B1");
                levels.add("C1");
                break;
            case "C1":
                levels.add("B2");
                levels.add("C2");
                break;
            case "C2":
                levels.add("C1");
                break;
        }
        
        return levels;
    }
}
