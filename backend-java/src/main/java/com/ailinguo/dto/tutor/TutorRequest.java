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
}

