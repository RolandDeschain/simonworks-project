/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.Test;
import org.simonworks.projects.utils.AssertionError;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class WebPathIteratorTest {

    @Test void testEmptyStringThanError() {
        assertThrows(AssertionError.class,
                () -> new WebPathIterator(null));
        assertThrows(AssertionError.class,
                () -> new WebPathIterator(""));
    }

    @Test void testSimplePath() {
        Iterator<String> i = new WebPathIterator("/");
        assertTrue(i.hasNext());
        assertEquals("/", i.next());
        assertFalse(i.hasNext());
    }

    @Test void testOneItem() {
        Iterator<String> i = new WebPathIterator("/foo");
        assertTrue(i.hasNext());
        assertEquals("/foo", i.next());
        assertFalse(i.hasNext());
    }

    @Test void testTwoItems() {
        Iterator<String> i = new WebPathIterator("/foo/bar");
        assertTrue(i.hasNext());
        assertEquals("/foo", i.next());
        assertTrue(i.hasNext());
        assertEquals("/bar", i.next());
        assertFalse(i.hasNext());
    }

    @Test void testOneItemAndVariable() {
        Iterator<String> i = new WebPathIterator("/foo/{var1}");
        assertTrue(i.hasNext());
        assertEquals("/foo", i.next());
        assertTrue(i.hasNext());
        assertEquals("/{var1}", i.next());
        assertFalse(i.hasNext());
    }
}