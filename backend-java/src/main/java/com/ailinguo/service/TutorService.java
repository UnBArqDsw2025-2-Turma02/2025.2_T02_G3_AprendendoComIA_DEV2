package com.ailinguo.service;

import com.ailinguo.dto.tutor.TutorRequest;
import com.ailinguo.dto.tutor.TutorResponse;
import com.ailinguo.model.ChatTurn;
import com.ailinguo.observer.UserProgressSubject;
import com.ailinguo.repository.ChatTurnRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TutorService {
    
    private final OpenAIService openAIService;
    private final ChatTurnRepository chatTurnRepository;
    private final UserProgressSubject userProgressSubject;
    
    public TutorService(OpenAIService openAIService, 
                       ChatTurnRepository chatTurnRepository,
                       UserProgressSubject userProgressSubject) {
        this.openAIService = openAIService;
        this.chatTurnRepository = chatTurnRepository;
        this.userProgressSubject = userProgressSubject;
    }
    
    public TutorResponse processTutorRequest(TutorRequest request, Long userId) {
        TutorResponse response = openAIService.generateTutorResponse(request);
        
        // Save chat history if session ID is provided
        if (userId != null && request.getSessionId() != null) {
            saveChatTurns(request, response, userId);
            
            // Notify observers about XP gain (10 XP per interaction)
            userProgressSubject.userGainedXp(userId, 10, 10);
            
            // Notify task completion
            userProgressSubject.userCompletedTask(userId, "CHAT_INTERACTION");
        }
        
        return response;
    }
    
    private void saveChatTurns(TutorRequest request, TutorResponse response, Long userId) {
        String sessionId = request.getSessionId();
        long timestamp = System.currentTimeMillis();
        
        // Save user turn
        ChatTurn userTurn = ChatTurn.builder()
                .id((long) (sessionId.hashCode() + timestamp))
                .role(ChatTurn.Role.USER)
                .text(request.getUserText())
                .createdAt(LocalDateTime.now())
                .build();
        
        // Convert response corrections and exercise
        List<ChatTurn.Correction> corrections = response.getCorrections().stream()
                .map(c -> ChatTurn.Correction.builder()
                        .original(c.getOriginal())
                        .corrected(c.getCorrected())
                        .explanation(c.getExplanation())
                        .rule(c.getRule())
                        .build())
                .collect(Collectors.toList());
        
        ChatTurn.MiniExercise exercise = null;
        if (response.getMiniExercise() != null) {
            TutorResponse.MiniExercise ex = response.getMiniExercise();
            exercise = ChatTurn.MiniExercise.builder()
                    .type(ex.getType())
                    .question(ex.getQuestion())
                    .options(ex.getOptions())
                    .correct(ex.getCorrect())
                    .explanation(ex.getExplanation())
                    .build();
        }
        
        // Save tutor turn
        ChatTurn tutorTurn = ChatTurn.builder()
                .id((long) (sessionId.hashCode() + timestamp + 1))
                .role(ChatTurn.Role.TUTOR)
                .text(response.getReply())
                .corrections(corrections)
                .exercise(exercise)
                .createdAt(LocalDateTime.now())
                .build();
        
        chatTurnRepository.saveAll(List.of(userTurn, tutorTurn));
    }
}

