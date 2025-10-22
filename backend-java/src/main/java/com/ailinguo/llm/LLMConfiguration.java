package com.ailinguo.llm;

import com.ailinguo.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Factory/Config do Adapter – escolhe o provider por propriedade. */
@Configuration
public class LLMConfiguration {

    @Value("${app.llm.provider:openai}")
    private String provider;

    @Bean
    public LLMProvider llmProvider(OpenAIService openAIService) {
        switch (provider.toLowerCase()) {
            case "mock": return new MockAdapter();
            case "gemini": return new GeminiAdapter();
            case "openai":
            default: return new OpenAIAdapter(openAIService);
        }
    }
}
