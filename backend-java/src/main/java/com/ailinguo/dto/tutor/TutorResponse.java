package com.ailinguo.dto.tutor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TutorResponse {
    private String reply;
    
    @Builder.Default
    private List<Correction> corrections = new ArrayList<>();
    
    private MiniExercise miniExercise;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Correction {
        private String original;
        private String corrected;
        private String explanation;
        private String rule;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MiniExercise {
        private String type;
        private String question;
        private List<String> options;
        private Integer correct;
        private String explanation;
    }
}

