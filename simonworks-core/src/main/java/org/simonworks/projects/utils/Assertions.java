/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.utils;

import java.util.Map;

public interface Assertions {

    class AssertionError extends Error {

        AssertionError(String message) {
            super(message);
        }

    }

    static void assertNotNull(Object obj) {
        assertNotNull(obj, StringUtils.EMPTY);
    }

    static void assertNotNull(Object obj, String message) {
        if (obj == null) {
            throw new AssertionError(message);
        }
    }

    static void assertNotEmpty(Map<String, String> m) {
        assertNotEmpty(m, StringUtils.EMPTY);
    }

    static void assertNotEmpty(Map<String, String> m, String message) {
        assertNotNull(m, message);
        if (m.isEmpty()) {
            throw new AssertionError(message);
        }
    }

    static void assertNotEmpty(Iterable<String> i) {
        assertNotEmpty(i, StringUtils.EMPTY);
    }

    static void assertNotEmpty(Iterable<String> i, String message) {
        assertNotNull(i, message);
        if (!i.iterator().hasNext()) {
            throw new AssertionError(message);
        }
    }

    static void assertTrue(boolean condition) {
        assertTrue(condition, StringUtils.EMPTY);
    }

    static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    static void assertFalse(boolean condition) {
        assertFalse(condition, StringUtils.EMPTY);
    }

    static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

}
