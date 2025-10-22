package com.ailinguo.llm;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;

/** Target (GoF Adapter) – contrato estável do domínio. */
public interface LLMProvider {
    TutorResponse tutor(TutorRequest request);
}
