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

class JsonNumberValueTest {

    @Test void testToString() {
        JsonElement<Number> number = JsonValue.jsonNumber(10);
        assertEquals(10, number.getRepresentedValue());
        assertEquals("10", number.toString());
    }

    @Test
    void testNullValue_thanException() {
        assertThrows(AssertionError.class, () -> JsonValue.jsonNumber((null)));
    }

}