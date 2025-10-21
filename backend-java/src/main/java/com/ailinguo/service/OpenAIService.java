package com.ailinguo.service;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    
    @Value("${app.openai.api-key}")
    private String apiKey;
    
    @Value("${app.openai.model}")
    private String model;
    
    @Value("${app.openai.mock-mode}")
    private boolean mockMode;
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public TutorResponse generateTutorResponse(TutorRequest request) {
        if (mockMode || apiKey == null || apiKey.isEmpty()) {
            logger.info("Using mock mode for tutor response");
            return getMockResponse(request);
        }
        
        try {
            OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));
            
            String systemPrompt = buildSystemPrompt();
            String userPrompt = String.format("User level: %s\nMode: %s\nUser text: \"\"\"%s\"\"\"\n\nRespond ONLY with valid JSON.", 
                    request.getUserLevel(), request.getMode(), request.getUserText());
            
            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .model(model)
                    .temperature(0.3)
                    .messages(Arrays.asList(
                            new ChatMessage("system", systemPrompt),
                            new ChatMessage("user", userPrompt)
                    ))
                    .build();
            
            String response = service.createChatCompletion(completionRequest)
                    .getChoices().get(0).getMessage().getContent();
            
            return parseResponse(response);
            
        } catch (Exception e) {
            logger.error("Error calling OpenAI API, falling back to mock", e);
            return getMockResponse(request);
        }
    }
    
    private String buildSystemPrompt() {
        return """
                You are an English tutor for Brazilian Portuguese speakers learning English.
                
                Your role:
                1. Respond naturally in English first
                2. Provide up to 3 corrections with brief explanations in Portuguese
                3. Create 1 quick exercise based on the user's input
                4. Be encouraging and motivating
                5. Adapt vocabulary and complexity to the user's CEFR level
                
                Format your response as JSON:
                {
                  "reply": "Natural English response",
                  "corrections": [
                    {
                      "original": "user's text",
                      "corrected": "corrected version", 
                      "explanation": "Brief explanation in Portuguese",
                      "rule": "Grammar rule name"
                    }
                  ],
                  "miniExercise": {
                    "type": "multiple_choice",
                    "question": "Question text",
                    "options": ["option1", "option2", "option3", "option4"],
                    "correct": 0,
                    "explanation": "Why this answer is correct"
                  }
                }
                
                Keep corrections to maximum 3 items. Be gentle and encouraging.
                """;
    }
    
    private TutorResponse parseResponse(String jsonResponse) throws JsonProcessingException {
        JsonNode root = objectMapper.readTree(jsonResponse);
        
        TutorResponse response = new TutorResponse();
        response.setReply(root.get("reply").asText());
        
        List<TutorResponse.Correction> corrections = new ArrayList<>();
        if (root.has("corrections")) {
            for (JsonNode corrNode : root.get("corrections")) {
                TutorResponse.Correction correction = TutorResponse.Correction.builder()
                        .original(corrNode.get("original").asText())
                        .corrected(corrNode.get("corrected").asText())
                        .explanation(corrNode.get("explanation").asText())
                        .rule(corrNode.has("rule") ? corrNode.get("rule").asText() : null)
                        .build();
                corrections.add(correction);
            }
        }
        response.setCorrections(corrections);
        
        if (root.has("miniExercise") && !root.get("miniExercise").isNull()) {
            JsonNode exNode = root.get("miniExercise");
            List<String> options = new ArrayList<>();
            for (JsonNode opt : exNode.get("options")) {
                options.add(opt.asText());
            }
            
            TutorResponse.MiniExercise exercise = TutorResponse.MiniExercise.builder()
                    .type(exNode.get("type").asText())
                    .question(exNode.get("question").asText())
                    .options(options)
                    .correct(exNode.get("correct").asInt())
                    .explanation(exNode.get("explanation").asText())
                    .build();
            response.setMiniExercise(exercise);
        }
        
        return response;
    }
    
    private TutorResponse getMockResponse(TutorRequest request) {
        TutorResponse response = new TutorResponse();
        response.setReply("That's a great start! Let me help you improve this sentence.");
        
        List<TutorResponse.Correction> corrections = new ArrayList<>();
        corrections.add(TutorResponse.Correction.builder()
                .original("I go to school yesterday")
                .corrected("I went to school yesterday")
                .explanation("Use past tense 'went' for actions that happened in the past")
                .rule("Past Simple Tense")
                .build());
        response.setCorrections(corrections);
        
        TutorResponse.MiniExercise exercise = TutorResponse.MiniExercise.builder()
                .type("multiple_choice")
                .question("Choose the correct past tense:")
                .options(Arrays.asList("I go", "I went", "I going", "I goes"))
                .correct(1)
                .explanation("Past tense of 'go' is 'went'")
                .build();
        response.setMiniExercise(exercise);
        
        return response;
    }
}

