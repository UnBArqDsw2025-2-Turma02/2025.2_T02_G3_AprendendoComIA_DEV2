package com.ailinguo.observer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Configuration class to register observers with the subject
 */
@Component
public class ObserverConfig {
    
    private final UserProgressSubject userProgressSubject;
    private final AchievementObserver achievementObserver;
    private final NotificationObserver notificationObserver;
    
    public ObserverConfig(UserProgressSubject userProgressSubject,
                         AchievementObserver achievementObserver,
                         NotificationObserver notificationObserver) {
        this.userProgressSubject = userProgressSubject;
        this.achievementObserver = achievementObserver;
        this.notificationObserver = notificationObserver;
    }
    
    @PostConstruct
    public void init() {
        userProgressSubject.attach(achievementObserver);
        userProgressSubject.attach(notificationObserver);
    }
}
