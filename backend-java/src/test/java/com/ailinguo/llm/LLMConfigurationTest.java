package com.ailinguo.llm;

import com.ailinguo.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class LLMConfigurationTest {

    @Mock
    private OpenAIService openAIService;

    private LLMConfiguration configuration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        configuration = new LLMConfiguration();
    }

    @Test
    @DisplayName("Deve retornar OpenAIAdapter quando provider é 'openai'")
    void testOpenAIProvider() {
        ReflectionTestUtils.setField(configuration, "provider", "openai");
        
        LLMProvider provider = configuration.llmProvider(openAIService);
        
        assertNotNull(provider);
        assertTrue(provider instanceof OpenAIAdapter);
    }

    @Test
    @DisplayName("Deve retornar MockAdapter quando provider é 'mock'")
    void testMockProvider() {
        ReflectionTestUtils.setField(configuration, "provider", "mock");
        
        LLMProvider provider = configuration.llmProvider(openAIService);
        
        assertNotNull(provider);
        assertTrue(provider instanceof MockAdapter);
    }

    @Test
    @DisplayName("Deve retornar GeminiAdapter quando provider é 'gemini'")
    void testGeminiProvider() {
        ReflectionTestUtils.setField(configuration, "provider", "gemini");
        
        LLMProvider provider = configuration.llmProvider(openAIService);
        
        assertNotNull(provider);
        assertTrue(provider instanceof GeminiAdapter);
    }

    @Test
    @DisplayName("Deve retornar OpenAIAdapter como padrão")
    void testDefaultProvider() {
        ReflectionTestUtils.setField(configuration, "provider", "unknown");
        
        LLMProvider provider = configuration.llmProvider(openAIService);
        
        assertNotNull(provider);
        assertTrue(provider instanceof OpenAIAdapter);
    }

    // TODO: Implementar os seguintes testes:
    // 1. testCaseInsensitive - Testar se aceita MOCK, Mock, OpenAI, etc
    // 2. testNewInstancesCreated - Verificar se cria instâncias diferentes
    // 3. testOpenAIAdapterReceivesService - Verificar se OpenAIAdapter recebe service
    // 4. testEmptyProvider - Testar com provider vazio
    // 5. testNullProvider - Testar com provider null
}
