/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.reflection.Typed;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BeanInfoTest {

    BeanInfo be;

    @BeforeEach
    void setUp() {
        be = new BeanInfo(new Typed(ExampleBean.class));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void aliases() {
        assertEquals(Arrays.asList("example"), be.aliases());
    }

    @Test
    void getBeanClass() {
        assertEquals(ExampleBean.class, be.getType().getRawType());
    }

    @Test
    void getDependencies() {
        assertFalse(be.getDependencies().isEmpty());
        // assertEquals("samples", be.getDependencies().get("samples").beanName());
        // assertEquals("", be.getDependencies().get("samples").afterInjectionMethod());
    }

    @Test
    void getInjectableBeanContext() {
        assertNull(be.getInjectableBeanContext());
    }

    @Test
    void getCompleteSetup() {
        assertNull(be.getCompleteSetup());
    }

    @Test
    void getLifecycle() {
        assertSame(BeanInfo.Lifecycle.SINGLETON, be.getLifecycle());
    }
}