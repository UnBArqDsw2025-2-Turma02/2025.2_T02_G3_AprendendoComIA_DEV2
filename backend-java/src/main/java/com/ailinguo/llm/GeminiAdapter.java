package com.ailinguo.llm;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;

/** Adapter para Gemini (esqueleto). Implemente quando integrar o SDK/HTTP. */
public class GeminiAdapter implements LLMProvider {
    @Override
    public TutorResponse tutor(TutorRequest request) {
        TutorResponse res = new TutorResponse();
        res.setReply("[Gemini] Not yet implemented");
        return res;
    }
}
