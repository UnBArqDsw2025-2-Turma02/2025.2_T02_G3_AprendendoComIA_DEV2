package com.ailinguo.controller;

import com.ailinguo.model.Exercise;
import com.ailinguo.model.UserExerciseAttempt;
import com.ailinguo.repository.ExerciseRepository;
import com.ailinguo.repository.UserExerciseAttemptRepository;
import com.ailinguo.repository.UserRepository;
import com.ailinguo.service.XpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserExerciseAttemptRepository attemptRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private XpService xpService;

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseRepository.findAll();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/cefr/{cefrLevel}")
    public ResponseEntity<List<Exercise>> getExercisesByCefrLevel(@PathVariable String cefrLevel) {
        List<Exercise> exercises = exerciseRepository.findByCefrLevel(cefrLevel);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/random/{cefrLevel}/{limit}")
    public ResponseEntity<List<Exercise>> getRandomExercises(
            @PathVariable String cefrLevel, 
            @PathVariable int limit) {
        List<Exercise> exercises = exerciseRepository.findRandomByCefrLevel(cefrLevel, limit);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        return exercise.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{exerciseId}/attempt")
    public ResponseEntity<Map<String, Object>> submitExerciseAttempt(
            @PathVariable Long exerciseId,
            @RequestBody Map<String, Object> request) {
        
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            List<Map<String, Object>> answers = (List<Map<String, Object>>) request.get("answers");
            Integer timeSpent = Integer.valueOf(request.get("timeSpent").toString());

            Optional<Exercise> exerciseOpt = exerciseRepository.findById(exerciseId);
            if (!exerciseOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Exercise exercise = exerciseOpt.get();
            
            // Calculate score
            int correctAnswers = 0;
            int totalQuestions = exercise.getQuestions().size();
            
            for (Map<String, Object> answer : answers) {
                Long questionId = Long.valueOf(answer.get("questionId").toString());
                String userAnswer = answer.get("answer").toString();
                
                // Find the correct answer for this question
                String correctAnswer = exercise.getQuestions().stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst()
                    .map(q -> q.getCorrectAnswer())
                    .orElse("");
                
                if (userAnswer.equals(correctAnswer)) {
                    correctAnswers++;
                }
            }
            
            BigDecimal score = BigDecimal.valueOf((double) correctAnswers / totalQuestions * 100);
            Integer xpEarned = (int) (score.doubleValue() / 100 * exercise.getXpReward());
            
            // Save attempt
            UserExerciseAttempt attempt = new UserExerciseAttempt();
            attempt.setUser(userRepository.findById(userId).orElse(null));
            attempt.setExercise(exercise);
            attempt.setScore(score);
            attempt.setXpEarned(xpEarned);
            attempt.setTimeSpent(timeSpent);
            attempt.setCompletedAt(LocalDateTime.now());
            
            attemptRepository.save(attempt);
            
            // Update user XP
            xpService.addXp(userId, xpEarned, "exercise", exerciseId, 
                "Completed exercise: " + exercise.getTitle());
            
            Map<String, Object> response = new HashMap<>();
            response.put("score", score);
            response.put("xpEarned", xpEarned);
            response.put("correctAnswers", correctAnswers);
            response.put("totalQuestions", totalQuestions);
            response.put("success", true);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/user/{userId}/attempts")
    public ResponseEntity<List<UserExerciseAttempt>> getUserAttempts(@PathVariable Long userId) {
        List<UserExerciseAttempt> attempts = attemptRepository.findByUserIdOrderByCompletedAtDesc(userId);
        return ResponseEntity.ok(attempts);
    }
}
