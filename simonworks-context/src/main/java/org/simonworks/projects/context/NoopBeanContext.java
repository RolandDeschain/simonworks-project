/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.utils.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Empty context implementing NullObject Pattern
 */
class NoopBeanContext implements BeanContext {

    private static final BeanContext EMPTY = new NoopBeanContext();

    private NoopBeanContext() {}

    @Override
    public BeanContext self() {
        return this;
    }

    public static final BeanContext get() {
        return EMPTY;
    }

    @Override
    public String getId() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public <T> T getBean(String name) {
        throw new BeanNotExistsException("Bean for name " + name + " is not available");
    }

    @Override
    public Set<String> beanNamesSet() {
        return new HashSet<>();
    }
}
