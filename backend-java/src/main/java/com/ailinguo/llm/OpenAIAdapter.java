package com.ailinguo.llm;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;
import com.ailinguo.service.OpenAIService;

/** Adapter concreto p/ OpenAI – delega ao serviço existente. */
public class OpenAIAdapter implements LLMProvider {
    private final OpenAIService openAIService;
    public OpenAIAdapter(OpenAIService openAIService) { this.openAIService = openAIService; }

    @Override
    public TutorResponse tutor(TutorRequest request) {
        return openAIService.generateTutorResponse(request);
    }
}
