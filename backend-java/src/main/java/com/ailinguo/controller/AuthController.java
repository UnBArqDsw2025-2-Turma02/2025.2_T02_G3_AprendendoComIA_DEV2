package com.ailinguo.controller;

import com.ailinguo.dto.UserDto;
import com.ailinguo.dto.auth.AuthResponse;
import com.ailinguo.dto.auth.LoginRequest;
import com.ailinguo.facade.IAILinguoFacade; // Importe a interface da Facade
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // A única dependência agora é a Facade
    private final IAILinguoFacade aiLinguoFacade;

    // Injeta a Facade através do construtor
    public AuthController(IAILinguoFacade aiLinguoFacade) {
        this.aiLinguoFacade = aiLinguoFacade;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody Map<String, Object> request,
            HttpServletResponse response
    ) {
        try {
            // Delega a chamada de registro para a Facade
            AuthResponse authResponse = aiLinguoFacade.registerUser(request, response);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            // Mantém o tratamento de erro (ou centraliza na Facade)
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
            // Delega a chamada de login para a Facade
            AuthResponse authResponse = aiLinguoFacade.loginUser(request, response);
            return ResponseEntity.ok(authResponse);
        } catch (Exception e) {
            // Mantém o tratamento de erro
            return ResponseEntity.status(401)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, UserDto>> getCurrentUser(Authentication authentication) {
        // Delega a busca do usuário atual para a Facade
        UserDto user = aiLinguoFacade.getCurrentAuthenticatedUser(authentication);
        // Retorna um DTO vazio se não autenticado, para consistência
        UserDto responseUser = user != null ? user : new UserDto();
        return ResponseEntity.ok(Map.of("user", responseUser));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Boolean>> logout(HttpServletResponse response) {
        // Delega a chamada de logout para a Facade
        aiLinguoFacade.logoutUser(response);
        return ResponseEntity.ok(Map.of("success", true));
    }
}