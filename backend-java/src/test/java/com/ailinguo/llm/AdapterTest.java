package com.ailinguo.llm;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;
import com.ailinguo.model.User;
import com.ailinguo.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AdapterTest {

    @Mock
    private OpenAIService openAIService;

    private TutorRequest sampleRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleRequest = new TutorRequest();
        sampleRequest.setUserText("I go to school yesterday");
        sampleRequest.setUserLevel(User.CefrLevel.A2);
    }

    @Test
    @DisplayName("MockAdapter deve retornar resposta mock válida")
    void testMockAdapter() {
        MockAdapter adapter = new MockAdapter();
        
        TutorResponse response = adapter.tutor(sampleRequest);
        
        assertNotNull(response);
        assertNotNull(response.getReply());
        assertTrue(response.getReply().contains("Mock"));
        assertNotNull(response.getCorrections());
        assertFalse(response.getCorrections().isEmpty());
        assertNotNull(response.getMiniExercise());
    }

    @Test
    @DisplayName("MockAdapter deve fazer correção de texto")
    void testMockAdapterCorrection() {
        MockAdapter adapter = new MockAdapter();
        
        TutorResponse response = adapter.tutor(sampleRequest);
        
        TutorResponse.Correction correction = response.getCorrections().get(0);
        assertNotNull(correction);
        assertTrue(correction.getCorrected().contains("went to"));
        assertNotNull(correction.getExplanation());
        assertNotNull(correction.getRule());
    }

    @Test
    @DisplayName("GeminiAdapter deve retornar mensagem de não implementado")
    void testGeminiAdapter() {
        GeminiAdapter adapter = new GeminiAdapter();
        
        TutorResponse response = adapter.tutor(sampleRequest);
        
        assertNotNull(response);
        assertNotNull(response.getReply());
        assertTrue(response.getReply().contains("Gemini"));
        assertTrue(response.getReply().contains("Not yet implemented"));
    }

    @Test
    @DisplayName("OpenAIAdapter deve delegar para OpenAIService")
    void testOpenAIAdapterDelegation() {
        TutorResponse mockResponse = new TutorResponse();
        mockResponse.setReply("OpenAI response");
        
        when(openAIService.generateTutorResponse(any(TutorRequest.class)))
            .thenReturn(mockResponse);
        
        OpenAIAdapter adapter = new OpenAIAdapter(openAIService);
        TutorResponse response = adapter.tutor(sampleRequest);
        
        assertNotNull(response);
        assertEquals("OpenAI response", response.getReply());
        verify(openAIService, times(1)).generateTutorResponse(sampleRequest);
    }

    @Test
    @DisplayName("Todos os adapters devem implementar LLMProvider")
    void testAllAdaptersImplementInterface() {
        MockAdapter mockAdapter = new MockAdapter();
        GeminiAdapter geminiAdapter = new GeminiAdapter();
        OpenAIAdapter openAIAdapter = new OpenAIAdapter(openAIService);
        
        assertTrue(mockAdapter instanceof LLMProvider);
        assertTrue(geminiAdapter instanceof LLMProvider);
        assertTrue(openAIAdapter instanceof LLMProvider);
    }

    // TODO: Implementar os seguintes testes:
    // 1. testMockAdapterMiniExercise - Verificar se MockAdapter gera mini exercício correto
    // 2. testOpenAIAdapterPassesCorrectRequest - Verificar se OpenAIAdapter passa request correto
    // 3. testMockAdapterWithNullText - Testar MockAdapter com texto null
    // 4. testMockAdapterCompleteResponse - Verificar resposta completa do MockAdapter
    // 5. testOpenAIAdapterWithNullResponse - Testar OpenAIAdapter com resposta null do service
}
