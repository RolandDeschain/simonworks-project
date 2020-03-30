/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

public class JsonObjectParser implements JsonParser<Object> {

    @Override
    public boolean canProduce(Class<?> clazz) {
        return false;
    }

    @Override
    public Object parse(JsonReader reader) throws JsonParseException {
        return null;
    }
}
