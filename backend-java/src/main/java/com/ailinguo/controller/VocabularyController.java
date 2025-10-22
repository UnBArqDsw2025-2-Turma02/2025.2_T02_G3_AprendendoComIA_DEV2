package com.ailinguo.controller;

import com.ailinguo.dto.vocabulary.VocabularyReviewRequest;
import com.ailinguo.dto.vocabulary.VocabularyReviewResponse;
import com.ailinguo.model.VocabularyCard;
import com.ailinguo.service.VocabularyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vocabulary")
public class VocabularyController {
    
    private final VocabularyService vocabularyService;
    
    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }
    
    @GetMapping("/due")
    public ResponseEntity<?> getDueCards(
            @RequestParam String userId,
            @RequestParam(defaultValue = "10") int limit,
            Authentication authentication
    ) {
        try {
            String authUserId = (String) authentication.getPrincipal();
            if (!authUserId.equals(userId)) {
                return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
            }
            
            List<VocabularyCard> cards = vocabularyService.getDueCards(Long.parseLong(userId), limit);
            return ResponseEntity.ok(cards);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }
    
    @PostMapping("/review")
    public ResponseEntity<?> reviewCard(
            @Valid @RequestBody VocabularyReviewRequest request,
            Authentication authentication
    ) {
        try {
            String authUserId = (String) authentication.getPrincipal();
            if (!authUserId.equals(request.getUserId())) {
                return ResponseEntity.status(403).body(Map.of("error", "Forbidden"));
            }
            
            VocabularyReviewResponse response = vocabularyService.reviewCard(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }
}

