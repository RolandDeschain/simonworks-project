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
import org.simonworks.projects.conversion.DeserializationException;
import org.simonworks.projects.conversion.SimpleBean;

import java.util.function.Function;

class JsonDeserializerOldTest {

    JsonDeserializer_old deserializer;

    @BeforeEach void setUp() {
        deserializer = new JsonDeserializer_old();
    }

    //@Test
    void deserializeJJA() throws DeserializationException {
        exec(s -> new JsonJavaReader(s));
    }

    //@Test
    void deserializeJCAR() throws DeserializationException {
        exec(s -> new JsonCharArrayReader(s));
    }

    void exec(Function<String, JsonReader> function) throws DeserializationException {
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