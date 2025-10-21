package com.ailinguo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chatSessions")
public class ChatSession {
    @Id
    private String id;
    
    private String userId;
    private User.CefrLevel level;
    private String topic;
    private String summary;
    
    private LocalDateTime createdAt;
}

