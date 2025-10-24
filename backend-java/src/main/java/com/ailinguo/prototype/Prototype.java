package com.ailinguo.prototype;

/**
 * Simple Prototype interface.
 * @param <T> type to be cloned
 */
public interface Prototype<T> {
    T clonePrototype();
}