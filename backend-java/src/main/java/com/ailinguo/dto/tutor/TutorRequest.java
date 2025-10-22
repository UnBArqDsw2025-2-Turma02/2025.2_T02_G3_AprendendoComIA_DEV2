package com.ailinguo.dto.tutor;

import com.ailinguo.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TutorRequest {
    @NotBlank(message = "User text is required")
    @Size(min = 1, max = 4000, message = "Text must be between 1 and 4000 characters")
    private String userText;
    
    private User.CefrLevel userLevel = User.CefrLevel.B1;
    private Mode mode = Mode.CONVERSATION;
    private String sessionId;
    
    public enum Mode {
        CONVERSATION, WRITING
    }
    
    // Getters e Setters manuais
    public String getUserText() { return userText; }
    public void setUserText(String userText) { this.userText = userText; }
    
    public User.CefrLevel getUserLevel() { return userLevel; }
    public void setUserLevel(User.CefrLevel userLevel) { this.userLevel = userLevel; }
    
    public Mode getMode() { return mode; }
    public void setMode(Mode mode) { this.mode = mode; }
    
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
}

