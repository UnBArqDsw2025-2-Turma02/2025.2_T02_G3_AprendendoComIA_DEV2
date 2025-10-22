package com.ailinguo.llm;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;

import java.util.Arrays;

/** Adapter mock */
public class MockAdapter implements LLMProvider {

    @Override
    public TutorResponse tutor(TutorRequest request) {
        TutorResponse res = new TutorResponse();
        String msg = request.getUserText() == null ? "" : request.getUserText();

        res.setReply("Mock: here is a friendly correction of your text.");
        TutorResponse.Correction c = TutorResponse.Correction.builder()
                .original(msg)
                .corrected(msg.replace("go to", "went to"))
                .explanation("Past tense correction (mock).")
                .rule("Simple Past of 'go' is 'went'.")
                .build();
        res.setCorrections(Arrays.asList(c));

        TutorResponse.MiniExercise ex = TutorResponse.MiniExercise.builder()
                .type("multiple_choice")
                .question("Choose the correct past of 'go'")
                .options(Arrays.asList("go", "went", "gone", "going"))
                .correct(1)
                .explanation("Past simple is 'went'.")
                .build();
        res.setMiniExercise(ex);
        return res;
    }
}
