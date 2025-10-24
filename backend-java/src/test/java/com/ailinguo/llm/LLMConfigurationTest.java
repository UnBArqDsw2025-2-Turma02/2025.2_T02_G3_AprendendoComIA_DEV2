package com.ailinguo.llm;

import com.ailinguo.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.util.ReflectionTestUtils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.*;


class LLMConfigurationTest {

    private OpenAIService openAIService;

    private LLMConfiguration configuration;

    @BeforeEach
    void setUp() {
        // Use a real instance (no Mockito) to avoid inline mocking issues on newer JDKs
        openAIService = new OpenAIService();
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

    @Test
    @DisplayName("Provider é case-insensitive")
    void testCaseInsensitive() {
        ReflectionTestUtils.setField(configuration, "provider", "MOCK");
        LLMProvider p1 = configuration.llmProvider(openAIService);
        assertNotNull(p1);
        assertTrue(p1 instanceof MockAdapter);

        ReflectionTestUtils.setField(configuration, "provider", "Mock");
        LLMProvider p2 = configuration.llmProvider(openAIService);
        assertNotNull(p2);
        assertTrue(p2 instanceof MockAdapter);

        ReflectionTestUtils.setField(configuration, "provider", "OpenAI");
        LLMProvider p3 = configuration.llmProvider(openAIService);
        assertNotNull(p3);
        assertTrue(p3 instanceof OpenAIAdapter);
    }

    @Test
    @DisplayName("Cria novas instâncias a cada chamada")
    void testNewInstancesCreated() {
        ReflectionTestUtils.setField(configuration, "provider", "mock");

        LLMProvider provider1 = configuration.llmProvider(openAIService);
        LLMProvider provider2 = configuration.llmProvider(openAIService);

        assertNotNull(provider1);
        assertNotNull(provider2);
        assertNotSame(provider1, provider2);
    }

    @Test
    @DisplayName("OpenAIAdapter recebe o service corretamente")
    void testOpenAIAdapterReceivesService() {
        ReflectionTestUtils.setField(configuration, "provider", "openai");

        LLMProvider provider = configuration.llmProvider(openAIService);

        assertNotNull(provider);
        assertTrue(provider instanceof OpenAIAdapter);
        // verificar que o serviço interno é o mesmo mock
        Object inner = ReflectionTestUtils.getField(provider, "openAIService");
        assertEquals(openAIService, inner);
    }

    @Test
    @DisplayName("Provider vazio usa default (OpenAIAdapter)")
    void testEmptyProvider() {
        ReflectionTestUtils.setField(configuration, "provider", "");

        LLMProvider provider = configuration.llmProvider(openAIService);

        assertNotNull(provider);
        assertTrue(provider instanceof OpenAIAdapter);
    }

    @Test
    @DisplayName("Provider null lança NullPointerException")
    void testNullProvider() {
        ReflectionTestUtils.setField(configuration, "provider", null);

        NullPointerException thrown = assertThrows(NullPointerException.class, () -> configuration.llmProvider(openAIService));
        assertNotNull(thrown);
    }

    @Test
    @DisplayName("Todos os providers implementam LLMProvider")
    void testAllProvidersImplementInterface() {
        ReflectionTestUtils.setField(configuration, "provider", "mock");
        LLMProvider mock = configuration.llmProvider(openAIService);
        
        ReflectionTestUtils.setField(configuration, "provider", "gemini");
        LLMProvider gemini = configuration.llmProvider(openAIService);
        
        ReflectionTestUtils.setField(configuration, "provider", "openai");
        LLMProvider openai = configuration.llmProvider(openAIService);
        
        assertTrue(mock instanceof LLMProvider);
        assertTrue(gemini instanceof LLMProvider);
        assertTrue(openai instanceof LLMProvider);
    }

    @Test
    @DisplayName("Provider com espaços retorna default OpenAIAdapter")
    void testProviderWithSpaces() {
        ReflectionTestUtils.setField(configuration, "provider", "  mock  ");

        LLMProvider provider = configuration.llmProvider(openAIService);

        assertNotNull(provider);
        // Como toLowerCase não faz trim, " mock " não match "mock", então retorna default
        assertTrue(provider instanceof OpenAIAdapter);
    }

    @Test
    @DisplayName("Provider desconhecido sempre retorna OpenAIAdapter")
    void testUnknownProviderReturnsDefault() {
        String[] unknownProviders = {"xyz", "test", "invalid", "123"};
        
        for (String unknown : unknownProviders) {
            ReflectionTestUtils.setField(configuration, "provider", unknown);
            LLMProvider provider = configuration.llmProvider(openAIService);
            
            assertNotNull(provider);
            assertTrue(provider instanceof OpenAIAdapter, 
                "Provider '" + unknown + "' should return OpenAIAdapter");
        }
    }


    
}
