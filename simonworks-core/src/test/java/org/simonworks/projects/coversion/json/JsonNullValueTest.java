/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonNullValueTest {

    @Test
    void testToString() {
        JsonElement jv = JsonValue.jsonNull();
        assertNull(jv.getRepresentedValue());
        JsonElement jv1 = JsonValue.jsonNull(10);
        assertNull(jv1.getRepresentedValue());
        JsonElement jv2 = JsonValue.jsonNull("kjfiojwiojfd");
        assertNull(jv2.getRepresentedValue());

        assertEquals("null", jv.toString());
        assertEquals("null", jv1.toString());
        assertEquals("null", jv2.toString());
    }

}