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
        
        // Getters e Setters manuais
        public String getOriginal() { return original; }
        public void setOriginal(String original) { this.original = original; }
        
        public String getCorrected() { return corrected; }
        public void setCorrected(String corrected) { this.corrected = corrected; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
        
        public String getRule() { return rule; }
        public void setRule(String rule) { this.rule = rule; }
        
        // Método builder manual
        public static CorrectionBuilder builder() {
            return new CorrectionBuilder();
        }
        
        public static class CorrectionBuilder {
            private String original;
            private String corrected;
            private String explanation;
            private String rule;
            
            public CorrectionBuilder original(String original) { this.original = original; return this; }
            public CorrectionBuilder corrected(String corrected) { this.corrected = corrected; return this; }
            public CorrectionBuilder explanation(String explanation) { this.explanation = explanation; return this; }
            public CorrectionBuilder rule(String rule) { this.rule = rule; return this; }
            
            public Correction build() {
                Correction correction = new Correction();
                correction.setOriginal(original);
                correction.setCorrected(corrected);
                correction.setExplanation(explanation);
                correction.setRule(rule);
                return correction;
            }
        }
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
        
        // Getters e Setters manuais
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
        
        public List<String> getOptions() { return options; }
        public void setOptions(List<String> options) { this.options = options; }
        
        public Integer getCorrect() { return correct; }
        public void setCorrect(Integer correct) { this.correct = correct; }
        
        public String getExplanation() { return explanation; }
        public void setExplanation(String explanation) { this.explanation = explanation; }
        
        // Método builder manual
        public static MiniExerciseBuilder builder() {
            return new MiniExerciseBuilder();
        }
        
        public static class MiniExerciseBuilder {
            private String type;
            private String question;
            private List<String> options;
            private Integer correct;
            private String explanation;
            
            public MiniExerciseBuilder type(String type) { this.type = type; return this; }
            public MiniExerciseBuilder question(String question) { this.question = question; return this; }
            public MiniExerciseBuilder options(List<String> options) { this.options = options; return this; }
            public MiniExerciseBuilder correct(Integer correct) { this.correct = correct; return this; }
            public MiniExerciseBuilder explanation(String explanation) { this.explanation = explanation; return this; }
            
            public MiniExercise build() {
                MiniExercise exercise = new MiniExercise();
                exercise.setType(type);
                exercise.setQuestion(question);
                exercise.setOptions(options);
                exercise.setCorrect(correct);
                exercise.setExplanation(explanation);
                return exercise;
            }
        }
    }
    
    // Getters e Setters manuais
    public String getReply() { return reply; }
    public void setReply(String reply) { this.reply = reply; }
    
    public List<Correction> getCorrections() { return corrections; }
    public void setCorrections(List<Correction> corrections) { this.corrections = corrections; }
    
    public MiniExercise getMiniExercise() { return miniExercise; }
    public void setMiniExercise(MiniExercise miniExercise) { this.miniExercise = miniExercise; }
}

