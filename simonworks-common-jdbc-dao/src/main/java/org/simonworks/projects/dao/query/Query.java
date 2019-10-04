/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;

public interface Query {

    static Insert insert() {
        return new InsertImpl();
    }

    static Update update() {
        return new UpdateImpl();
    }

    static Delete delete() {
        return new DeleteImpl();
    }

    static Select select() {
        return new SelectImpl();
    }

    static void main(String[] args) {
        Arrays.asList(
                insert().into("example").columns("a", "b", "c").buildQuery(),
                update().table("example").set("a", "b", "c").where("a").buildQuery(),
                update().table("example").set("a", "b", "c").where().buildQuery(),
                delete().from("example").where("a").buildQuery(),
                delete().from("example").where().buildQuery(),
                select().columns("a", "b", "c").from("example").where("c", "e").buildQuery(),
                select().count().columns().from("example").where().buildQuery()
        ).forEach(System.out::println);
    }
}
