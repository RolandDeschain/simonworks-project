/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion.json;

import org.junit.jupiter.api.Test;
import org.simonworks.projects.utils.AssertionError;

import static org.junit.jupiter.api.Assertions.*;

class JsonValueTest {

    @Test
    void getValue() {
        Object o = new Object();
        JsonElement json = new JsonObject(o);
        assertEquals(o, json.getRepresentedValue());
    }

    @Test
    void testNullValue_thanException() {
        assertThrows(AssertionError.class, () -> new JsonObject<>(null));
    }

    @Test
    void testToString() {
        Object o = new Object();
        JsonElement json = new JsonObject(o);
        assertNotNull(json.toString());
        System.out.println(json.toString());
    }
}