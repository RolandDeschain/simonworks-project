/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

class JsonBooleanParser implements JsonParser<Boolean> {

    @Override
    public boolean canProduce(Class<?> clazz) {
        return Boolean.class == clazz || boolean.class == clazz;
    }

    @Override
    public Boolean parse(JsonReader reader) throws JsonParseException {
        reader.skipWhiteSpaces();
        if(reader.actual() == 't') {
            return tryReadTrue(reader);
        } else if(reader.actual() == 'f') {
            return tryReadFalse(reader);
        }
        throw new JsonParseException("Boolean value not found at index " + reader.index() + ". Actual char is " + (char) reader.actual());
    }

    private Boolean tryReadFalse(JsonReader reader) throws JsonParseException {
        return tryRead(reader, "false", Boolean.FALSE);
    }

     private Boolean tryReadTrue(JsonReader reader) throws JsonParseException {
         return tryRead(reader, "true", Boolean.TRUE);
    }

    private Boolean tryRead(JsonReader reader, String aString, Boolean successReturn) {
        int count = JsonParser.matchesNext(reader, aString);
        if(count != aString.length()) {
            reader.unread(count);
            return null;
        }
        return successReturn;
    }

}
