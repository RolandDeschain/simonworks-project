/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

public interface JsonParser<O> {

    boolean canProduce(Class<?> clazz);

    O parse(JsonReader reader) throws JsonParseException;

    static int matchesNext(JsonReader reader, String aString) {
        assertNotNull(aString, "String to test cannot be null!");
        return matchesNext(reader, aString.toCharArray());
    }

    static int matchesNext(JsonReader reader, char[] chars) {
        assertNotNull(chars, "Chars to test cannot be null!");
        int count = 0;
        for(char c : chars) {
            if(matchesNext(reader, c) != 1) {
                break;
            }
            count++;
        }
        return count;
    }

    static int matchesNext(JsonReader reader, char c) {
        assertNotNull(reader, "JsonReader cannot be null");
        int count = c == reader.actual() ? 1 : 0;
        if(count == 1 && !reader.isClosed()) {
            try {
                reader.nextChar();
            } catch (JsonReadException e) {
                /**
                 * Can't happen!
                 */
            }
        }
        return count;
    }

    static void throwsExceptionForUnexpectedToken(JsonReader reader) throws JsonParseException {
        throw new JsonParseException(
                "Unexpected token <" + (char)reader.actual()
                        + "> codepoint <" + reader.actual()
                        + "> at index " + reader.index());
    }

    static void throwsExceptionForUnexpectedToken(JsonReader reader, Exception root) throws JsonParseException {
        throw new JsonParseException(
                "Unexpected token <" + (char)reader.actual()
                + "> codepoint <" + reader.actual()
                + "> at index " + reader.index(), root);
    }

}
