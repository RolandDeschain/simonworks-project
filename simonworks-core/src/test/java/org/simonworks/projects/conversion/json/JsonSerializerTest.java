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

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.DeserializationException;
import org.simonworks.projects.conversion.SimpleBean;

import java.util.ArrayList;
import java.util.List;

class JsonSerializerTest {

    private JsonSerializer ser;
    private JsonDeserializer des;

    @BeforeEach void setUp() {

        ser = new JsonSerializer();
        des = new JsonDeserializer();
    }

    @Test
    void serialize() throws DeserializationException {

        List<SimpleBean> list = new ArrayList<>();
        list.add(new SimpleBean("Cars", "10", 25.7, false));
        list.add(new SimpleBean("Balloons", null, -34.89, true, (short)18));

        Gson gson = new Gson();
        String s = gson.toJson(list);

        String json = ser.serialize(list);
        System.out.println( json );

        List<SimpleBean> list1 = gson.fromJson(json, list.getClass());

        List<? extends SimpleBean> deserialize = des.deserialize(json, (Class<List<? extends SimpleBean>>) list.getClass());
        System.out.println(deserialize);

    }
}