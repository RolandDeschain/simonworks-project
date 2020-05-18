/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

/**
 * Noop singleton cache implementing NullObject Pattern
 */
public class NoopSingletonCache implements SingletonsCache {

    private static SingletonsCache instance = new NoopSingletonCache();

    private NoopSingletonCache() {}

    public static SingletonsCache get() { return instance; }

    @Override
    public Object get(String name) {
        return null;
    }

    @Override
    public void put(String name, Object obj) {
        /**
         * Do nothing ;-)
         */
    }
}
