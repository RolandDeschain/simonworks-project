/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BeanFactoryTest {

    private BeanFactory factory;
    private boolean configInvoked;
    private boolean postConstructInvoked;

    @BeforeEach
    public void setUp() {
        factory = new DefaultBeanFactory();
    }

    @Test
    void create() {
        Example e = factory.create("org.simonworks.projects.core.Example");
        Assertions.assertTrue(e.configInvoked);
        Assertions.assertTrue(e.postConstructInvoked);
        Assertions.assertEquals(e.a, 1);
    }

}