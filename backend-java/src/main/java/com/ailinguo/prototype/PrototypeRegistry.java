package com.ailinguo.prototype;

import com.ailinguo.model.User;

/**
 * Simple registry for our prototypes. If later the project needs
 * more than one default profile (e.g., teacher/admin), we can
 * register multiple keys here.
 */
public class PrototypeRegistry {

    private static final UserProfilePrototype DEFAULT_USER = new UserProfilePrototype();

    private PrototypeRegistry() {}

    public static UserProfilePrototype defaultUserProfile() {
        return DEFAULT_USER;
    }
}