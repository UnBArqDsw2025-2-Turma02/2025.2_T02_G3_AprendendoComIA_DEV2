package com.ailinguo.prototype;

import com.ailinguo.model.User;

import java.time.LocalDateTime;

/**
 * Concrete prototype that knows how to clone a default user profile.
 * We keep only the defaults here so AuthService can request a clone
 * and override identity-specific fields (email, name, password, cefr).
 */
public class UserProfilePrototype implements Prototype<User> {

    // Defaults for a "fresh" user profile
    private final int defaultDailyGoalMinutes;
    private final int defaultStreakDays;
    private final int defaultTotalMinutes;
    private final int defaultTotalXp;
    private final int defaultLevel;

    public UserProfilePrototype() {
        this.defaultDailyGoalMinutes = 15;
        this.defaultStreakDays = 0;
        this.defaultTotalMinutes = 0;
        this.defaultTotalXp = 0;
        this.defaultLevel = 1;
    }

    @Override
    public User clonePrototype() {
        // Return a *clean* user (no personal identifiers set).
        return User.builder()
                .dailyGoalMinutes(defaultDailyGoalMinutes)
                .streakDays(defaultStreakDays)
                .totalMinutes(defaultTotalMinutes)
                .totalXp(defaultTotalXp)
                .level(defaultLevel)
                .createdAt(LocalDateTime.now())
                .build();
    }

    /**
     * Convenience: clone and set identity-specific fields.
     */
    public User cloneWith(String email, String name, String passwordHash, User.CefrLevel cefrLevel) {
        User base = clonePrototype();
        base.setEmail(email);
        base.setName(name);
        base.setPassword(passwordHash);
        base.setCefrLevel(cefrLevel);
        return base;
    }
}