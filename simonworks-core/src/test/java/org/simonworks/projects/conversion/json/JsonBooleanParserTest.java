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

class JsonBooleanParserTest extends JsonParsersTest {

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
        JsonReader reader = getReader(aString);
        assertThrows(JsonParseException.class,
                () -> conv.parse(reader));
    }

    private void testInvalidValue(String aValue) throws JsonParseException {
        JsonReader reader = getReader(aValue);
        assertNull(conv.parse(reader));
        assertTrue(reader.index() <= 0);
    }

    private void testFalse(String aFalse) throws JsonParseException {
        JsonReader reader = getReader(aFalse);
        Boolean b = conv.parse(reader);
        assertFalse(b);
    }

    private void testTrue(String aTrue) throws JsonParseException {
        JsonReader reader = getReader(aTrue);
        Boolean b = conv.parse(reader);
        assertTrue(b);
    }

    JsonReader getReader(String s) {
        return getJJR_Reader(s);
    }

}