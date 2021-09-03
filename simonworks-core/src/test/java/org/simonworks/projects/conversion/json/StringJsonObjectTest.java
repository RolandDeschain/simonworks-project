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

import com.cedarsoftware.util.io.JsonReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.owlike.genson.Genson;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.SimpleBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class StringJsonObjectTest {

    //@Test
    void deserialize() throws IOException {

        List<SimpleBean> list = new ArrayList<>();
        list.add(create());
        list.add(create());

//        JsonObject<SimpleBean> sb = JsonObject.map(father);
//        String json = sb.toString();
//        System.out.println(json);

        String json = new Gson().toJson(list);

//        {
//            long amount = 0L;
//            for (int i = 0; i < 500; i++) {
//                try {
//                    long start = System.currentTimeMillis();
//                    JsonDeserializer<SimpleBean> jd = new JsonDeserializer<>();
//                    SimpleBean simple2 = jd.deserialize(json, SimpleBean.class);
//                    amount += System.currentTimeMillis() - start;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("SimonWorks avg time = " + (amount/50));
//        }
//
        {
            long amount = 0L;
            try {
                for (int i = 0; i < 500; i++) {

                    long start = System.currentTimeMillis();
                    Gson gson = new Gson();
                    List<SimpleBean> list1 = gson.fromJson(json, list.getClass());
                    amount += System.currentTimeMillis() - start;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Gson avg time = " + (amount / 50));
        }

        {
            long amount = 0L;
            try {
                for (int i = 0; i < 500; i++) {

                    long start = System.currentTimeMillis();
                    ObjectMapper om = new ObjectMapper();
                    List<SimpleBean> list1 = om.readValue(json, list.getClass());
                    amount += System.currentTimeMillis() - start;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Jackson avg time = " + (amount / 50));
      }

        {
            long amount = 0L;
            try {
                for (int i = 0; i < 500; i++) {

                    long start = System.currentTimeMillis();
                    Object o = JsonReader.jsonToJava(json);
                    amount += System.currentTimeMillis() - start;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Json-io avg time = " + (amount / 50));
        }

        {
            long amount = 0L;
            try {
                for (int i = 0; i < 500; i++) {

                    long start = System.currentTimeMillis();
                    Genson g = new Genson();
                    List list1 = g.deserialize(json, list.getClass());
                    amount += System.currentTimeMillis() - start;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Genson avg time = " + (amount / 50));
        }

//        {
//            long amount = 0L;
//            while(true) {
//                try {
//                    long start = System.currentTimeMillis();
//                    Gson gson = new Gson();
//                    SimpleBean simple2 = gson.fromJson(json, SimpleBean.class);
//                    amount += System.currentTimeMillis() - start;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }

    private SimpleBean create() {
        SimpleBean father = new SimpleBean("Simone", "45");
        father.setChild(new SimpleBean("Edoardo", "1", 2d, true, (short)90));
        father.setBeans(new SimpleBean[] {
                new SimpleBean("Cars", "10", 25.7, false),
                new SimpleBean("Balloons", null, -34.89, true, (short)18)
        });
        return father;
    }
}