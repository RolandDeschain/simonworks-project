/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonStringParserTest extends JsonParsersTest {

    JsonParser<String> conv;

    @BeforeEach void setup() {
        conv = new JsonStringParser();
    }

    @Test
    void parse() throws JsonParseException {
        testString("Simon", "Simon");
        testString("\"Simon\"", "Simon");
        testString("'Simon'", "Simon");
        testString("                 Simon", "Simon");
        testString("Simon                 ", "Simon");
        testString("         Simon          ", "Simon");
        testString("Simon\"", "Simon");
        testString("Simon'", "Simon");
    }

    @Test
    void parseInvalidString_thanException() {
        testInvalidString("\"Simon");
        testInvalidString("'Simon");
    }

    @Test
    void canConvertTo() {
        assertTrue(conv.canProduce(String.class));
        assertFalse(conv.canProduce(char.class));
        assertFalse(conv.canProduce(Number.class));
        assertFalse(conv.canProduce(Object.class));
        assertFalse(conv.canProduce(Double.class));
        assertFalse(conv.canProduce(String[].class));
    }

    void testString(String aString, String expected) throws JsonParseException {
        JsonReader reader = getReader(aString);
        String result = conv.parse(reader);
        assertEquals(expected, result);
    }

    void testInvalidString(String aString) {
        JsonReader reader = getReader(aString);
        assertThrows(JsonParseException.class,
                () -> conv.parse(reader));
        assertTrue(reader.index() <= 0);
    }

    JsonReader getReader(String s) {
        return getJJR_Reader(s);
    }
}