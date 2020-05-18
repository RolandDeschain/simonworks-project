/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.utils;

import java.util.Map;

public final class Assertions {

    private Assertions() {}

    static void assertNotNull(Object obj) {
        assertNotNull(obj, StringUtils.EMPTY);
    }

    public static void assertNotNull(Object obj, String message) {
        if(obj == null) {
            throw new AssertionError((message));
        }
    }

    public static void assertNotEmpty(Map<?, ?> m) {
        assertNotEmpty(m, StringUtils.EMPTY);
    }

    public static void assertNotEmpty(Map<?, ?> m, String message) {
        assertNotNull(m, message);
        if(m.isEmpty()) {
            throw new AssertionError((message));
        }
    }

    public static void assertNotEmpty(Iterable<?> i) {
        assertNotEmpty(i, StringUtils.EMPTY);
    }

    public static void assertNotEmpty(Iterable<?> i, String message) {
        assertNotNull(i, message);
        if(!i.iterator().hasNext()) {
            throw new AssertionError((message));
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue(condition, StringUtils.EMPTY);
    }

    public static void assertTrue(boolean condition, String message) {
        if(!condition) {
            throw new AssertionError((message));
        }
    }

    public static void assertFalse(boolean condition) {
        assertFalse(condition, StringUtils.EMPTY);
    }

    public static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }

}
