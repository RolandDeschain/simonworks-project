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
        return new StringBuilder()
                .append("INSERT INTO ")
                .append(getTable())
                .append(" ( ")
                .append(
                        Arrays
                                .stream(getColumns())
                                .map(c -> c.name())
                )
                .append(" ) VALUES ( ")
                .append(Arrays
                        .stream(getColumns())
                        .map(s -> "?")
                        .collect(Collectors.joining(", ")))
                .append(" )")
                .toString();
    }
}
