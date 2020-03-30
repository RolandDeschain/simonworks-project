/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

public interface BeanRegistry {

    void registerBean(Class<?> clazz);

    void unregisterBean(String name);

    BeanInfo getBeanInfo(String name);

}
