package com.ailinguo.llm;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;
import com.ailinguo.model.User;
import com.ailinguo.service.OpenAIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class AdapterTest {

    private OpenAIService openAIService;

    private TutorRequest sampleRequest;

    // Simple stub to avoid Mockito inline mocking on newer JDKs
    private static class StubOpenAIService extends OpenAIService {
        public TutorRequest lastRequest;
        public TutorResponse toReturn;

        @Override
        public TutorResponse generateTutorResponse(TutorRequest request) {
            this.lastRequest = request;
            return toReturn;
        }

        public void setReturn(TutorResponse r) { this.toReturn = r; }
    }

    @BeforeEach
    void setUp() {
        // use stub instead of Mockito mock to avoid Byte Buddy/Java version issues
        openAIService = new StubOpenAIService();
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
        // configure stub to return the prepared response
        ((StubOpenAIService) openAIService).setReturn(mockResponse);

        OpenAIAdapter adapter = new OpenAIAdapter(openAIService);
        TutorResponse response = adapter.tutor(sampleRequest);

        assertNotNull(response);
        assertEquals("OpenAI response", response.getReply());
        // ensure the stub recorded the request
        assertSame(sampleRequest, ((StubOpenAIService) openAIService).lastRequest);
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

    @Test
    @DisplayName("MockAdapter gera mini exercise correto")
    void testMockAdapterMiniExercise() {
        MockAdapter adapter = new MockAdapter();

        TutorResponse response = adapter.tutor(sampleRequest);

        assertNotNull(response.getMiniExercise());
        TutorResponse.MiniExercise ex = response.getMiniExercise();
        assertEquals("multiple_choice", ex.getType());
        assertNotNull(ex.getQuestion());
        assertNotNull(ex.getOptions());
        assertEquals(4, ex.getOptions().size());
        assertEquals(1, ex.getCorrect());
        assertNotNull(ex.getExplanation());
    }

    @Test
    @DisplayName("OpenAIAdapter passa request correto para o service")
    void testOpenAIAdapterPassesCorrectRequest() {
        OpenAIAdapter adapter = new OpenAIAdapter(openAIService);
        adapter.tutor(sampleRequest);

        StubOpenAIService stub = (StubOpenAIService) openAIService;
        assertNotNull(stub.lastRequest);
        assertEquals("I go to school yesterday", stub.lastRequest.getUserText());
        assertEquals(User.CefrLevel.A2, stub.lastRequest.getUserLevel());
    }

    @Test
    @DisplayName("MockAdapter trata texto null sem lançar exceção")
    void testMockAdapterWithNullText() {
        MockAdapter adapter = new MockAdapter();
        TutorRequest req = new TutorRequest();
        req.setUserText(null);

        TutorResponse response = adapter.tutor(req);

        assertNotNull(response);
        assertNotNull(response.getReply());
    }

    @Test
    @DisplayName("MockAdapter retorna resposta completa (reply, corrections, miniExercise)")
    void testMockAdapterCompleteResponse() {
        MockAdapter adapter = new MockAdapter();

        TutorResponse response = adapter.tutor(sampleRequest);

        assertAll(
                () -> assertNotNull(response.getReply()),
                () -> assertNotNull(response.getCorrections()),
                () -> assertFalse(response.getCorrections().isEmpty()),
                () -> assertNotNull(response.getMiniExercise())
        );
    }

    @Test
    @DisplayName("OpenAIAdapter retorna null quando service retorna null")
    void testOpenAIAdapterWithNullResponse() {
        ((StubOpenAIService) openAIService).setReturn(null);

        OpenAIAdapter adapter = new OpenAIAdapter(openAIService);
        TutorResponse response = adapter.tutor(sampleRequest);

        assertNull(response);
    }

    @Test
    @DisplayName("MockAdapter mantém texto original na correção")
    void testMockAdapterPreservesOriginalText() {
        MockAdapter adapter = new MockAdapter();
        
        TutorResponse response = adapter.tutor(sampleRequest);
        
        TutorResponse.Correction correction = response.getCorrections().get(0);
        assertEquals(sampleRequest.getUserText(), correction.getOriginal());
    }

    @Test
    @DisplayName("GeminiAdapter sempre retorna resposta não null")
    void testGeminiAdapterNeverNull() {
        GeminiAdapter adapter = new GeminiAdapter();
        
        TutorResponse response1 = adapter.tutor(sampleRequest);
        TutorResponse response2 = adapter.tutor(new TutorRequest());
        
        assertNotNull(response1);
        assertNotNull(response2);
    }

    @Test
    @DisplayName("MockAdapter options contém resposta correta")
    void testMockAdapterCorrectAnswer() {
        MockAdapter adapter = new MockAdapter();
        
        TutorResponse response = adapter.tutor(sampleRequest);
        TutorResponse.MiniExercise ex = response.getMiniExercise();
        
        assertEquals("went", ex.getOptions().get(ex.getCorrect()));
    }

    @Test
    @DisplayName("Todos adapters tratam diferentes modos de request")
    void testAdaptersWithDifferentModes() {
        sampleRequest.setMode(TutorRequest.Mode.WRITING);
        
        MockAdapter mockAdapter = new MockAdapter();
        GeminiAdapter geminiAdapter = new GeminiAdapter();
        
        TutorResponse mockResponse = mockAdapter.tutor(sampleRequest);
        TutorResponse geminiResponse = geminiAdapter.tutor(sampleRequest);
        
        assertNotNull(mockResponse);
        assertNotNull(geminiResponse);
    }
}
