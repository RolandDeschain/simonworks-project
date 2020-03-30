/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.DeserializationException;
import org.simonworks.projects.conversion.Deserializer;
import org.simonworks.projects.coversion.SimpleBean;

import static org.junit.jupiter.api.Assertions.*;

class JsonDeserializerTest {

    Deserializer<String, SimpleBean> deserializer;

    @BeforeEach void setUp() {
        deserializer = new JsonDeserializer<SimpleBean>();
    }

    @Test
    void deserialize() throws DeserializationException {
        SimpleBean father = new SimpleBean("Simone", "45");
        father.setChild(new SimpleBean("Edoardo", "1", 2d, true, (short)90));
        father.setBeans(new SimpleBean[] {
                new SimpleBean("Cars", "10", 25.7, false),
                new SimpleBean("Balloons", null, -34.89, true, (short)18)
        });
        System.out.println(father.toString());
        JsonObject<SimpleBean> sb = JsonObject.map(father);
        System.out.println(sb.toString());

        SimpleBean deserialized = deserializer.deserialize(sb.toString(), SimpleBean.class);
        System.out.println(deserialized);
    }
}