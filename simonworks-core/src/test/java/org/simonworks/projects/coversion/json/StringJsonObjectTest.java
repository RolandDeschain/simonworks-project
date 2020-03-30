/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.coversion.SimpleBean;

import java.io.IOException;

class StringJsonObjectTest {

    @Test
    void deserialize() throws IOException {
        SimpleBean father = new SimpleBean("Simone", "45");
        father.setChild(new SimpleBean("Edoardo", "1", 2d, true, (short)90));
        father.setBeans(new SimpleBean[] {
                new SimpleBean("Cars", "10", 25.7, false),
                new SimpleBean("Balloons", null, -34.89, true, (short)18)
        });
        JsonObject<SimpleBean> sb = JsonObject.map(father);
        String json = sb.toString();
        System.out.println(json);

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
//        {
//            long amount = 0L;
//            for (int i = 0; i < 500; i++) {
//                try {
//                    long start = System.currentTimeMillis();
//                    Gson gson = new Gson();
//                    SimpleBean simple2 = gson.fromJson(json, SimpleBean.class);
//                    amount += System.currentTimeMillis() - start;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            System.out.println("Gson avg time = " + (amount / 50));
//        }

        {
            long amount = 0L;
            while(true) {
                try {
                    long start = System.currentTimeMillis();
                    JsonDeserializer<SimpleBean> jd = new JsonDeserializer<>();
                    SimpleBean simple2 = jd.deserialize(json, SimpleBean.class);
                    amount += System.currentTimeMillis() - start;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
}