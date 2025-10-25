package com.ailinguo.observer;

/**
 * Subject interface - manages observers and notifies them
 */
public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers(String event, Object data);
}
