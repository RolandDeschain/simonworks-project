/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractHierarchicalTest {

    private AbstractHierarchical<Object> h;

    @BeforeEach public void beforeEach() {
        h = new AbstractHierarchical<Object>() {
            @Override
            public Object getNoopParent() {
                return null;
            }
        };
    }

    @Test
    void getParent() {
        Object p = new Object();
        h.setParent(p);
        Assertions.assertSame(p, h.getParent());
        h.setParent(new Object());
        assertNotSame(p, h.getParent());
    }

    @Test void testNullParent() {
        assertNull(h.getParent());
    }
}