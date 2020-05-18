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

import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.conversion.Serializer;

import java.lang.reflect.Type;

@Prototype(name = "jsonDeserializer")
public class JsonSerializer implements Serializer {

    @Override
    public String serialize(Object input) {
        JsonObject map = JsonObject.map(input);
        return map.toString();
    }
}
