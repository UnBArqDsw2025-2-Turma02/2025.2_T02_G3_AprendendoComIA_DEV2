package com.ailinguo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    
    private final List<Map<String, Object>> quizQuestions;
    
    public TaskController() {
        quizQuestions = Arrays.asList(
                Map.of(
                        "id", "q1",
                        "prompt", "Choose the correct past form: \"Yesterday I ___ to the store.\"",
                        "options", Arrays.asList("go", "goes", "gone", "went"),
                        "answerIndex", 3,
                        "explanation", "Past simple of \"go\" is \"went\"."
                ),
                Map.of(
                        "id", "q2",
                        "prompt", "Select the correct sentence:",
                        "options", Arrays.asList(
                                "She don't like coffee.",
                                "She doesn't likes coffee.",
                                "She doesn't like coffee.",
                                "She not like coffee."
                        ),
                        "answerIndex", 2,
                        "explanation", "\"She doesn't like\" is the correct negative form in present simple."
                ),
                Map.of(
                        "id", "q3",
                        "prompt", "Fill the gap: \"I have a lot of ___ with English grammar.\"",
                        "options", Arrays.asList("difficult", "difficulty", "difficulties", "difficultly"),
                        "answerIndex", 1,
                        "explanation", "Use the noun \"difficulty\" after \"have\"."
                ),
                Map.of(
                        "id", "q4",
                        "prompt", "Choose the uncountable noun:",
                        "options", Arrays.asList("advices", "information", "peoples", "furnitures"),
                        "answerIndex", 1,
                        "explanation", "\"Information\" is uncountable (no plural form)."
                ),
                Map.of(
                        "id", "q5",
                        "prompt", "Capitalize correctly: \"i speak english.\"",
                        "options", Arrays.asList(
                                "I speak english.",
                                "I speak English.",
                                "i speak English.",
                                "I Speak English."
                        ),
                        "answerIndex", 1,
                        "explanation", "Capitalize \"I\" and \"English\"."
                )
        );
    }
    
    @GetMapping("/quiz")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getQuiz() {
        return ResponseEntity.ok(Map.of("questions", quizQuestions));
    }
}

