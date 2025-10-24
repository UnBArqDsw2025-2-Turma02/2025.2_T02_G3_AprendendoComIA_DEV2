package com.ailinguo.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Concrete Observer - handles user notifications
 */
@Component
public class NotificationObserver implements Observer {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationObserver.class);
    
    @Override
    public void update(String event, Object data) {
        switch (event) {
            case "XP_GAINED" -> notifyXpGained((UserProgressSubject.XpData) data);
            case "LEVEL_UP" -> notifyLevelUp((UserProgressSubject.LevelData) data);
            case "TASK_COMPLETED" -> notifyTaskCompleted((UserProgressSubject.TaskData) data);
        }
    }
    
    private void notifyXpGained(UserProgressSubject.XpData data) {
        logger.info("📧 Notification: Sending XP notification to user {}: +{} XP", 
                    data.userId(), data.xpGained());
        // In production: send email, push notification, etc.
    }
    
    private void notifyLevelUp(UserProgressSubject.LevelData data) {
        logger.info("📧 Notification: Congratulations! User {} is now level {}!", 
                    data.userId(), data.newLevel());
        // In production: send email, push notification, etc.
    }
    
    private void notifyTaskCompleted(UserProgressSubject.TaskData data) {
        logger.info("📧 Notification: User {} completed: {}", 
                    data.userId(), data.taskType());
        // In production: send email, push notification, etc.
    }
}
