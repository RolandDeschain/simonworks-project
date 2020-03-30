/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;
import java.util.stream.Collectors;

class InsertImpl extends AbstractStatement implements Insert, Columns<QueryComplete> {

    @Override
    public Columns<QueryComplete> into(String table) {
        setTable(table);
        return this;
    }

    @Override
    public QueryComplete columns(Column ... columns) {
        setColumns(columns);
        return this;
    }

    @Override
    public String buildQuery() {
        return "INSERT INTO " +
                getTable() +
                " ( " +
                Arrays
                        .stream(getColumns())
                        .map(Column::name)
                        .collect(Collectors.joining(", ")) +
                " ) VALUES ( " +
                Arrays
                        .stream(getColumns())
                        .map(s -> "?")
                        .collect(Collectors.joining(", ")) +
                " )";
    }
}
