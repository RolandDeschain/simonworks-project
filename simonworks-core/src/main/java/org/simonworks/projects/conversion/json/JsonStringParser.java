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

class JsonStringParser implements JsonParser<String> {

    @Override
    public boolean canProduce(Class<?> clazz) {
        return String.class == clazz;
    }

    @Override
    public String parse(JsonReader reader) throws JsonParseException {
        String result = null;
        int i = reader.index();
        try {
            reader.skipWhiteSpaces();
            int actual = reader.actual();
            if(actual == 34 || actual == 39) {
                result = JsonReader.readWithQuote(reader, actual);
            } else {
                result = JsonReader.readWithoutQuote(reader);
            }
        } catch (JsonReadException e) {
            reader.unread(reader.index() - i);
            throw new JsonParseException(e);
        }
        return result;
    }
}
