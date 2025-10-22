package com.ailinguo.controller;

import com.ailinguo.dto.UserDto;
import com.ailinguo.dto.auth.AuthResponse;
import com.ailinguo.dto.auth.LoginRequest;
import com.ailinguo.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody Map<String, Object> request,
            HttpServletResponse response
    ) {
        try {
            AuthResponse authResponse = authService.register(request, response);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        try {
            AuthResponse authResponse = authService.login(request, response);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<Map<String, UserDto>> getCurrentUser(Authentication authentication) {
        String userId = authentication != null ? (String) authentication.getPrincipal() : null;
        UserDto user = authService.getCurrentUser(userId);
        return ResponseEntity.ok(Map.of("user", user != null ? user : new UserDto()));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Map<String, Boolean>> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok(Map.of("success", true));
    }
}

