/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

/**
 * Sharable context for {@link State}s allowing them to pass each other information among transitions.
 */
public interface StatesContext {

    String PREVIOUS_STATE_KEY = "PREVIOUS_STATE";

    /**
     * Recovers and returns a value from this context.
     *
     * @param key
     *  Key relative to a value.
     * @param <V>
     *     Generic type of the value associated to the key.
     * @return
     *  The value associated to the key.
     */
    <V> V get(String key);

    /**
     * Adds a new value in this context and associates it to the key.
     *
     * @param key
     *  Key associated to the value.
     * @param value
     *  The value associated to the key.
     */
    void put(String key, Object value);

    /**
     * Empties this context
     */
    void clear();
}
