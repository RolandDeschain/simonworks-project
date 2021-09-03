/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

public interface BeanContext extends ListableBeans{

    String getId();

    String getName();

    <T> T getBean(String name);

    BeanContext self();

}
