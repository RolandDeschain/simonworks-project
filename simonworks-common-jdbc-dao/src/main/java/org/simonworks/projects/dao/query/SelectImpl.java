/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;
import java.util.stream.Collectors;

class SelectImpl extends AbstractStatement implements Select, From, Where {

    private boolean count;

    @Override
    public From columns(Column ... columns) {
        if(columns == null || columns.length == 0) {
            setColumns();
        } else {
            setColumns(columns);
        }
        return this;
    }

    @Override
    public Where from(String table) {
        setTable(table);
        return this;
    }

    @Override
    public QueryComplete where(Column ... columns) {
        setWhereConditions(columns);
        return this;
    }

    @Override
    public Select count() {
        count = true;
        return this;
    }

    @Override
    public String buildQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        if(count) {
            sb.append("count(");
        }
        sb.append(Arrays
                    .stream(getColumns())
                    .map(column -> column.name())
                    .collect(Collectors.joining(","))
        );
        if(count) {
            sb.append(")");
        }
        sb
                .append(" FROM ")
                .append(getTable());
        if(getWhereConditions() != null) {
            sb
                    .append(" WHERE ")
                    .append(Arrays
                            .stream(getWhereConditions())
                            .map(c -> c.name() + c.operator())
                            .collect(Collectors.joining(" AND "))
                    );
        }
        return sb.toString();
    }

}
