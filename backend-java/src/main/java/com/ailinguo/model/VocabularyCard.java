package com.ailinguo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vocabularyCards")
public class VocabularyCard {
    @Id
    private String id;
    
    private String term;
    private String meaning;
    private String example;
    private User.CefrLevel cefrLevel;
}

