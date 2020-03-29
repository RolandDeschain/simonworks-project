/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import java.util.StringJoiner;

public class ExampleBean {

    private String branch;
    private String id;
    private int amount;

    public ExampleBean(String branch, String id, int amount) {
        this.branch = branch;
        this.id = id;
        this.amount = amount;
    }

    public String getBranch() {
        return branch;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ExampleBean.class.getSimpleName() + "[", "]")
                .add("branch='" + branch + "'")
                .add("id='" + id + "'")
                .add("amount=" + amount)
                .toString();
    }
}
