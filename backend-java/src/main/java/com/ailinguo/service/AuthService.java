package com.ailinguo.service;

import com.ailinguo.config.JwtUtil;
import com.ailinguo.dto.UserDto;
import com.ailinguo.dto.auth.AuthResponse;
import com.ailinguo.dto.auth.LoginRequest;
import com.ailinguo.dto.auth.RegisterRequest;
import com.ailinguo.model.User;
import com.ailinguo.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    public AuthResponse register(RegisterRequest request, HttpServletResponse response) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists");
        }
        
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .cefrLevel(request.getCefrLevel() != null ? request.getCefrLevel() : User.CefrLevel.A2)
                .dailyGoalMinutes(15)
                .streakDays(0)
                .totalMinutes(0)
                .createdAt(LocalDateTime.now())
                .build();
        
        userRepository.save(user);
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        setAuthCookie(response, token);
        
        return AuthResponse.builder()
                .user(UserDto.fromUser(user))
                .build();
    }
    
    public AuthResponse login(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getEmail());
        setAuthCookie(response, token);
        
        return AuthResponse.builder()
                .user(UserDto.fromUser(user))
                .build();
    }
    
    public UserDto getCurrentUser(String userId) {
        if (userId == null) {
            return null;
        }
        
        return userRepository.findById(userId)
                .map(UserDto::fromUser)
                .orElse(null);
    }
    
    public void logout(HttpServletResponse response) {
        clearAuthCookie(response);
    }
    
    private void setAuthCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
        cookie.setSecure(false); // Set true in production with HTTPS
        response.addCookie(cookie);
    }
    
    private void clearAuthCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token", "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

