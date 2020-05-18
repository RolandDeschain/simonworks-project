/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;
import java.util.stream.Collectors;

class UpdateImpl extends AbstractStatement implements Update, Set, Where {

    @Override
    public Where set(Column ... columns) {
        setColumns(columns);
        return this;
    }

    @Override
    public Set table(String table) {
        setTable(table);
        return this;
    }

    @Override
    public QueryComplete where(Column ... columns) {
        setWhereConditions(columns);
        return this;
    }
    @Override
    public String buildQuery() {
        StringBuilder sb = new StringBuilder()
                .append("UPDATE ")
                .append(getTable())
                .append(" ( ")
                .append(Arrays
                        .stream(getColumns())
                        .map(Column::name)
                        .collect(Collectors.joining(", ")))
                .append(" ) SET ( ")
                .append(Arrays
                        .stream(getColumns())
                        .map(s -> "?")
                        .collect(Collectors.joining(", ")))
                .append(" )");
        if(getWhereConditions() != null) {
            sb
                    .append(" WHERE ")
                    .append(Arrays
                            .stream(getWhereConditions())
                            .map(c -> c.name() + c.operator() + "?")
                            .collect(Collectors.joining(" AND ")));
        }
        return sb.toString();
    }

}
