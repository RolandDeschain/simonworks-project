/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.coversion.SimpleBean;

import static org.junit.jupiter.api.Assertions.*;

class ReflectionSupportTest {

    private SimpleBean sb;

    @BeforeEach void setUp() {
        sb = new SimpleBean();
    }

    @Test
    void invokeVoidMethod() {
        Object r = ReflectionSupport.invokeMethod(sb, "setName", "Simone");
        System.out.println(r);
        r = ReflectionSupport.invokeMethod(sb, "setName", null, null);
        System.out.println(r);
    }

    @Test
    void invokeMethodWithReturn() {
    }

    @Test
    void writeValue() {
    }

    @Test
    void checkField() {
    }

    @Test
    void readFields() {
    }
}