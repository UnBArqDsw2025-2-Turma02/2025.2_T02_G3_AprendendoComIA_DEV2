package com.ailinguo.observer;

/**
 * Observer interface - receives notifications from Subject
 */
public interface Observer {
    void update(String event, Object data);
}
