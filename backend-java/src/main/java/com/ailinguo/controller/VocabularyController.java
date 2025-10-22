package com.ailinguo.controller;

import com.ailinguo.model.VocabularyCategory;
import com.ailinguo.model.VocabularyWord;
import com.ailinguo.model.UserVocabularyProgress;
import com.ailinguo.repository.VocabularyCategoryRepository;
import com.ailinguo.repository.VocabularyWordRepository;
import com.ailinguo.repository.UserVocabularyProgressRepository;
import com.ailinguo.repository.UserRepository;
import com.ailinguo.service.XpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vocabulary")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class VocabularyController {

    @Autowired
    private VocabularyWordRepository vocabularyWordRepository;

    @Autowired
    private VocabularyCategoryRepository categoryRepository;

    @Autowired
    private UserVocabularyProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private XpService xpService;

    @GetMapping("/categories")
    public ResponseEntity<List<VocabularyCategory>> getAllCategories() {
        List<VocabularyCategory> categories = categoryRepository.findAllByOrderByNameAsc();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/words")
    public ResponseEntity<List<VocabularyWord>> getAllWords() {
        List<VocabularyWord> words = vocabularyWordRepository.findAll();
        return ResponseEntity.ok(words);
    }

    @GetMapping("/words/cefr/{cefrLevel}")
    public ResponseEntity<List<VocabularyWord>> getWordsByCefrLevel(@PathVariable String cefrLevel) {
        List<VocabularyWord> words = vocabularyWordRepository.findByCefrLevel(cefrLevel);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/words/category/{categoryId}")
    public ResponseEntity<List<VocabularyWord>> getWordsByCategory(@PathVariable Long categoryId) {
        List<VocabularyWord> words = vocabularyWordRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/words/random/{cefrLevel}/{limit}")
    public ResponseEntity<List<VocabularyWord>> getRandomWords(
            @PathVariable String cefrLevel, 
            @PathVariable int limit) {
        List<VocabularyWord> words = vocabularyWordRepository.findRandomByCefrLevel(cefrLevel, limit);
        return ResponseEntity.ok(words);
    }

    @GetMapping("/words/random/category/{categoryId}/{limit}")
    public ResponseEntity<List<VocabularyWord>> getRandomWordsByCategory(
            @PathVariable Long categoryId, 
            @PathVariable int limit) {
        List<VocabularyWord> words = vocabularyWordRepository.findRandomByCategory(categoryId, limit);
        return ResponseEntity.ok(words);
    }

    @PostMapping("/words/{wordId}/study")
    public ResponseEntity<Map<String, Object>> studyWord(
            @PathVariable Long wordId,
            @RequestBody Map<String, Object> request) {
        
        try {
            Long userId = Long.valueOf(request.get("userId").toString());
            Boolean correct = Boolean.valueOf(request.get("correct").toString());

            Optional<VocabularyWord> wordOpt = vocabularyWordRepository.findById(wordId);
            if (!wordOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            VocabularyWord word = wordOpt.get();
            
            // Find or create progress
            Optional<UserVocabularyProgress> progressOpt = progressRepository
                .findByUserIdAndVocabularyWordId(userId, wordId);
            
            UserVocabularyProgress progress;
            if (progressOpt.isPresent()) {
                progress = progressOpt.get();
            } else {
                progress = new UserVocabularyProgress();
                progress.setUser(userRepository.findById(userId).orElse(null));
                progress.setVocabularyWord(word);
                progress.setMasteryLevel(0);
                progress.setTimesStudied(0);
                progress.setXpEarned(0);
            }
            
            // Update progress
            progress.setTimesStudied(progress.getTimesStudied() + 1);
            progress.setLastStudied(LocalDateTime.now());
            
            Integer xpEarned = 0;
            if (correct) {
                progress.setMasteryLevel(Math.min(5, progress.getMasteryLevel() + 1));
                xpEarned = word.getXpReward();
                progress.setXpEarned(progress.getXpEarned() + xpEarned);
            } else {
                progress.setMasteryLevel(Math.max(0, progress.getMasteryLevel() - 1));
            }
            
            progressRepository.save(progress);
            
            // Update user XP
            if (xpEarned > 0) {
                xpService.addXp(userId, xpEarned, "vocabulary", wordId, 
                    "Studied word: " + word.getEnglishWord());
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("xpEarned", xpEarned);
            response.put("masteryLevel", progress.getMasteryLevel());
            response.put("timesStudied", progress.getTimesStudied());
            response.put("success", true);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/user/{userId}/progress")
    public ResponseEntity<List<UserVocabularyProgress>> getUserProgress(@PathVariable Long userId) {
        List<UserVocabularyProgress> progress = progressRepository.findByUserId(userId);
        return ResponseEntity.ok(progress);
    }

    @GetMapping("/user/{userId}/incomplete")
    public ResponseEntity<List<UserVocabularyProgress>> getIncompleteWords(@PathVariable Long userId) {
        List<UserVocabularyProgress> incomplete = progressRepository.findIncompleteByUserId(userId);
        return ResponseEntity.ok(incomplete);
    }

    @GetMapping("/user/{userId}/mastered")
    public ResponseEntity<List<UserVocabularyProgress>> getMasteredWords(@PathVariable Long userId) {
        List<UserVocabularyProgress> mastered = progressRepository.findMasteredByUserId(userId);
        return ResponseEntity.ok(mastered);
    }
}