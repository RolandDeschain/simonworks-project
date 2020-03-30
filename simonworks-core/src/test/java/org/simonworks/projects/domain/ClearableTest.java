/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClearableTest {

    class MyClearable implements Clearable<String> {

        private List<String> l = new ArrayList<>();

        /**
         * Returns an iterator over elements of type {@code T}.
         *
         * @return an Iterator.
         */
        @Override
        public Iterator<String> iterator() {
            return l.iterator();
        }

        public void add(String s) {
            l.add(s);
        }
    }

    MyClearable c = new MyClearable();

    @Test
    void clear() {
        assertFalse(c.iterator().hasNext());
        c.add("string");
        assertTrue(c.iterator().hasNext());
        c.clear();
        assertFalse(c.iterator().hasNext());

        Arrays.asList("a", "b", "c", "d").forEach(c::add);
        assertTrue(c.iterator().hasNext());
        c.clear();
        assertFalse(c.iterator().hasNext());
    }
}