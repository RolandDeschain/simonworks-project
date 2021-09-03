/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion.json;

import com.google.gson.Gson;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.conversion.DeserializationException;
import org.simonworks.projects.conversion.Deserializer;
import org.simonworks.projects.reflection.Typed;

@Singleton
public class JsonDeserializer implements Deserializer {

    private Gson gson = new Gson();

    @Override
    public <T> T deserialize(String input, Typed<T> typed) throws DeserializationException {
        return gson.fromJson(input, typed.getType());
    }
}
