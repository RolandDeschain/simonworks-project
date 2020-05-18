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
import org.simonworks.projects.conversion.DeserializationException;
import org.simonworks.projects.conversion.SimpleBean;

import java.util.function.Function;

class JsonDeserializerTest {

    JsonDeserializer deserializer;

    @BeforeEach void setUp() {
        deserializer = new JsonDeserializer();
    }

    @Test
    void deserializeJJA() throws DeserializationException {
        test(s -> new JsonJavaReader(s));
    }

    @Test
    void deserializeJCAR() throws DeserializationException {
        test(s -> new JsonCharArrayReader(s));
    }

    void test(Function<String, JsonReader> function) throws DeserializationException {
        SimpleBean father = new SimpleBean("Simone", "45");
        father.setChild(new SimpleBean("Edoardo", "1", 2d, true, (short)90));
        father.setBeans(new SimpleBean[] {
                new SimpleBean("Cars", "10", 25.7, false),
                new SimpleBean("Balloons", null, -34.89, true, (short)18)
        });
        System.out.println(father.toString());
        JsonObject<SimpleBean> sb = JsonObject.map(father);
        System.out.println(sb.toString());

        SimpleBean deserialized = deserializer.deserialize(function.apply(sb.toString()), SimpleBean.class);
        System.out.println(deserialized);
    }
}