/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.DeserializationException;

import static org.junit.jupiter.api.Assertions.*;

class JsonBooleanParserTest {

    JsonParser<Boolean> conv;

    @BeforeEach void setUp() {
        conv = new JsonBooleanParser();
    }

    @Test
    void canConvertTo() {
        assertTrue(conv.canProduce(Boolean.class));
        assertTrue(conv.canProduce(boolean.class));
        assertFalse(conv.canProduce(String.class));
    }

    @Test
    void testDeserializeFalse() throws JsonParseException {
        testFalse("false");
    }

    @Test
    void testDeserializeTrue() throws JsonParseException {
        testTrue("true");
    }

    @Test
    void testInvalidBoolean_thanException() throws JsonParseException {
        testException("Fa  lse ");
        testException("F alse ");
        testInvalidValue("falsE");
        testException("    False");
        testInvalidValue("tru e ");
        testException("TR ue");
        testException("TRUE");
        testException("TRue");
        testInvalidValue("truE");
    }

    private void testException(String aString) {
        JsonReader reader = new JsonCharArrayReader(aString);
        assertThrows(JsonParseException.class,
                () -> conv.parse(reader));
    }

    private void testInvalidValue(String aValue) throws JsonParseException {
        JsonReader reader = new JsonCharArrayReader(aValue);
        assertNull(conv.parse(reader));
        assertEquals(0, reader.index());
    }

    private void testFalse(String aFalse) throws JsonParseException {
        JsonReader reader = new JsonCharArrayReader(aFalse);
        Boolean b = conv.parse(reader);
        assertFalse(b);
    }

    private void testTrue(String aTrue) throws JsonParseException {
        JsonReader reader = new JsonCharArrayReader(aTrue);
        Boolean b = conv.parse(reader);
        assertTrue(b);
    }
}