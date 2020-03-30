/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

import org.simonworks.projects.utils.Assertions;

import java.util.HashMap;
import java.util.Map;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

/**
 * {@link StatesContext} implementation that uses a {@link java.util.Map} as underlying information store structure.
 */
public class MapStatesContext implements StatesContext {

    private Map<String, Object> context;

    public MapStatesContext() {
        this(new HashMap<>());
    }

    public MapStatesContext(Map<String, Object> context) {
        assertNotNull(context, "Underlying map cannot be null!");
        this.context = context;
    }

    public MapStatesContext(String key, Object value) {
        this();
        put(key, value);
    }

    /**
     * Recovers and returns a value from this context.
     *
     * @param key Key relative to a value.
     * @return The value associated to the key.
     */
    @Override
    public <V> V get(String key) {
        return (V) context.get(key);
    }

    /**
     * Adds a new value in this context and associates it to the key.
     *
     * @param key   Key associated to the value.
     * @param value The value associated to the key.
     */
    @Override
    public void put(String key, Object value) {
        assertNotNull(key, "Null keys are not accepted!");
        assertNotNull(value, "Null values are not accepted!");
        context.put(key, value);
    }

    @Override
    public void clear() {
        context.clear();
    }
}
