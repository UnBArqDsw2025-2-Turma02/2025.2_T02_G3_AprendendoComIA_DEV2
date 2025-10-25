package com.ailinguo.observer;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Subject - notifies observers about user progress changes
 */
@Component
public class UserProgressSubject implements Subject {
    
    private final List<Observer> observers = new ArrayList<>();
    
    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }
    
    @Override
    public void notifyObservers(String event, Object data) {
        for (Observer observer : observers) {
            observer.update(event, data);
        }
    }
    
    // Business methods that trigger notifications
    public void userGainedXp(Long userId, Integer xpGained, Integer totalXp) {
        notifyObservers("XP_GAINED", new XpData(userId, xpGained, totalXp));
    }
    
    public void userLeveledUp(Long userId, Integer newLevel) {
        notifyObservers("LEVEL_UP", new LevelData(userId, newLevel));
    }
    
    public void userCompletedTask(Long userId, String taskType) {
        notifyObservers("TASK_COMPLETED", new TaskData(userId, taskType));
    }
    
    // Data classes
    public record XpData(Long userId, Integer xpGained, Integer totalXp) {}
    public record LevelData(Long userId, Integer newLevel) {}
    public record TaskData(Long userId, String taskType) {}
}
