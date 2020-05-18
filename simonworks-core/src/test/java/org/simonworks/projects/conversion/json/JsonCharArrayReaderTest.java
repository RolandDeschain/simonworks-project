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
import org.simonworks.projects.conversion.SimpleBean;

import static org.junit.jupiter.api.Assertions.*;

class JsonCharArrayReaderTest {

    JsonReader reader;

    @Test
    void nextChar() {
    }

    @Test
    void readUntil() {
        JsonObject<SimpleBean> sb = JsonObject.map(new SimpleBean("Simone", "45"));
        String json = sb.toString();

        reader = new JsonCharArrayReader(json);
        try {
            assertEquals(123, reader.nextChar());
        } catch (JsonReadException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(" ", reader.readUntil(c -> c == ' '));
        } catch (JsonReadException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = reader.readUntil(c -> c != ' ' && c != ':');
        } catch (JsonReadException e) {
            e.printStackTrace();
        }
        assertEquals("number", s);
    }
}