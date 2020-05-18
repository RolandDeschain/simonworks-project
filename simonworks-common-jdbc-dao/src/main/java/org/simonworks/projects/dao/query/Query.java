/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

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
}
