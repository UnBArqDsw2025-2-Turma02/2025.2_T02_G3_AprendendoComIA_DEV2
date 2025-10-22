package com.ailinguo.dto.auth;

import com.ailinguo.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private UserDto user;
    
    // Getters e Setters manuais
    public UserDto getUser() { return user; }
    public void setUser(UserDto user) { this.user = user; }
    
    // Método builder manual
    public static AuthResponseBuilder builder() {
        return new AuthResponseBuilder();
    }
    
    public static class AuthResponseBuilder {
        private UserDto user;
        
        public AuthResponseBuilder user(UserDto user) { this.user = user; return this; }
        
        public AuthResponse build() {
            AuthResponse response = new AuthResponse();
            response.setUser(user);
            return response;
        }
    }
}

