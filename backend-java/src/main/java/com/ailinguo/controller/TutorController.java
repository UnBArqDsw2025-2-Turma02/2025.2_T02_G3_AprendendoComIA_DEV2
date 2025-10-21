package com.ailinguo.controller;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;
import com.ailinguo.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {
    
    private final TutorService tutorService;
    
    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }
    
    @PostMapping
    public ResponseEntity<?> processTutorRequest(
            @Valid @RequestBody TutorRequest request,
            Authentication authentication
    ) {
        try {
            String userId = authentication != null ? (String) authentication.getPrincipal() : null;
            TutorResponse response = tutorService.processTutorRequest(request, userId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Internal server error"));
        }
    }
}

