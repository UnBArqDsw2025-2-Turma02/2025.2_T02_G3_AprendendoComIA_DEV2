package com.ailinguo.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Concrete Observer - handles achievement notifications
 */
@Component
public class AchievementObserver implements Observer {
    
    private static final Logger logger = LoggerFactory.getLogger(AchievementObserver.class);
    
    @Override
    public void update(String event, Object data) {
        switch (event) {
            case "XP_GAINED" -> handleXpGained((UserProgressSubject.XpData) data);
            case "LEVEL_UP" -> handleLevelUp((UserProgressSubject.LevelData) data);
            case "TASK_COMPLETED" -> handleTaskCompleted((UserProgressSubject.TaskData) data);
        }
    }
    
    private void handleXpGained(UserProgressSubject.XpData data) {
        logger.info("🎯 Achievement: User {} gained {} XP! Total: {}", 
                    data.userId(), data.xpGained(), data.totalXp());
        
        // Check for XP milestones
        if (data.totalXp() >= 100 && data.totalXp() - data.xpGained() < 100) {
            logger.info("🏆 Achievement Unlocked: First 100 XP!");
        }
    }
    
    private void handleLevelUp(UserProgressSubject.LevelData data) {
        logger.info("⭐ Achievement: User {} reached level {}!", 
                    data.userId(), data.newLevel());
        
        if (data.newLevel() == 5) {
            logger.info("🏆 Achievement Unlocked: Intermediate Learner!");
        }
    }
    
    private void handleTaskCompleted(UserProgressSubject.TaskData data) {
        logger.info("✅ Achievement: User {} completed task: {}", 
                    data.userId(), data.taskType());
    }
}
