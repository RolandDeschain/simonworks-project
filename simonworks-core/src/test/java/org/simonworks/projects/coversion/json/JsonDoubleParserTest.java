/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonDoubleParserTest extends JsonParsersTest {

    JsonParser<Double> parser;

    @BeforeEach void setUp() {
        parser = new NumberParsers.JsonDoubleParser();
    }

    @Test
    void canProduce() {
        assertTrue(parser.canProduce(Double.class));
        assertTrue(parser.canProduce(double.class));
        assertFalse(parser.canProduce(Number.class));
    }

    @Test
    void parse() throws JsonParseException {
        testDouble("10.1", 10.1d);
        testDouble("0", 0.0d);
        testDouble("     1.68168761876", 1.68168761876d);
        testDouble("2.85E-4", 2.85E-4d);
        testDouble("2.85E-4               ", 2.85E-4d);
    }

    @Test void parseInvalid_thanException() {
        testInvalid(" 1000s ");
        testInvalid("10.9.7");
    }

    private void testDouble(String s, double v) throws JsonParseException {
        JsonReader reader = getReader(s);
        assertEquals(v, parser.parse(reader));
    }

    private void testInvalid(String s) {
        JsonReader reader = getReader(s);
        assertThrows(JsonParseException.class,
                () -> parser.parse(reader));
        assertTrue(reader.index() <= 0);
    }

    JsonReader getReader(String s) {
        return getJJR_Reader(s);
    }
}