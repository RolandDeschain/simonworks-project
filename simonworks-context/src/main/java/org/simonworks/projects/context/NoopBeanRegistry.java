/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.reflection.Typed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Empty registry implementing NullObject Pattern
 */
class NoopBeanRegistry implements BeanRegistry {

    private static BeanRegistry instance = new NoopBeanRegistry();

    private NoopBeanRegistry() {}

    public static BeanRegistry get() {
        return instance;
    }

    @Override
    public void registerBean(Class<?> clazz) {
        /**
         * Do nothing ;-)
         */
    }

    @Override
    public void registerBean(Typed<?> type) {
        /**
         * Do nothing ;-)
         */
    }

    @Override
    public void unregisterBean(String name) {
        /**
         * Do nothing ;-)
         */
    }

    @Override
    public BeanInfo getBeanInfo(String name) {
        throw new BeanNotExistsException("Bean info for name " + name + " is not available");
    }

    @Override
    public Set<String> beanNamesSet() {
        return new HashSet<>();
    }
}
