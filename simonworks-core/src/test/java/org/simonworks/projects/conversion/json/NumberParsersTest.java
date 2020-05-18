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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberParsersTest {

    JsonParser<Short> parser;

    @BeforeEach void setUp() {
        parser = new NumberParsers.JsonShortParser();
    }

    @Test void parse() throws JsonParseException {
        testShort("0", (short) 0);
        testShort("-1", (short) -1);
    }

    void testShort(String aShort, short value) throws JsonParseException {
        JsonReader reader = new JsonCharArrayReader(aShort);
        Short theShort = parser.parse(reader);
        assertEquals(value, theShort.shortValue());
    }
}