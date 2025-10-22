package com.ailinguo.service;

import com.ailinguo.model.User;
import com.ailinguo.model.UserXpLog;
import com.ailinguo.repository.UserRepository;
import com.ailinguo.repository.UserXpLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class XpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserXpLogRepository xpLogRepository;

    @Transactional
    public void addXp(Long userId, Integer xpAmount, String sourceType, Long sourceId, String description) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Update user XP
        Integer currentXp = user.getTotalXp() != null ? user.getTotalXp() : 0;
        Integer newXp = currentXp + xpAmount;
        user.setTotalXp(newXp);

        // Calculate new level (every 100 XP = 1 level)
        Integer newLevel = (newXp / 100) + 1;
        user.setLevel(newLevel);

        userRepository.save(user);

        // Log XP change
        UserXpLog xpLog = new UserXpLog();
        xpLog.setUser(user);
        xpLog.setXpChange(xpAmount);
        xpLog.setSourceType(sourceType);
        xpLog.setSourceId(sourceId);
        xpLog.setDescription(description);

        xpLogRepository.save(xpLog);
    }

    @Transactional
    public void subtractXp(Long userId, Integer xpAmount, String sourceType, Long sourceId, String description) {
        addXp(userId, -xpAmount, sourceType, sourceId, description);
    }

    public Integer getUserTotalXp(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getTotalXp() : 0;
    }

    public Integer getUserLevel(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user != null ? user.getLevel() : 1;
    }
}
