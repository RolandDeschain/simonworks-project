/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.reflection.Typed;

public interface BeanRegistry extends ListableBeans {

    default void registerBean(Class<?> clazz) {
        registerBean(new Typed<>(clazz));
    }

    void registerBean(Typed<?> type);

    void unregisterBean(String name);

    BeanInfo getBeanInfo(String name);

}
