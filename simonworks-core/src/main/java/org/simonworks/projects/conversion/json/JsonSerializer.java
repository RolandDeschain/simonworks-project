/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion.json;

import com.google.gson.Gson;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.conversion.Serializer;

@Singleton(name = "jsonSerializer")
public class JsonSerializer implements Serializer {

    private Gson gson = new Gson();

    @Override
    public <I> String serialize(I input) {
        return gson.toJson(input);
    }
}
