/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;
import java.util.stream.Collectors;

class DeleteImpl extends AbstractStatement implements Delete, Where {

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
    public String buildQuery() {
        StringBuilder sb = new StringBuilder();
        if( getWhereConditions() == null ) {
            sb
                    .append("TRUNCATE TABLE ")
                    .append(getTable());
        } else {
            sb
                    .append("DELETE FROM ")
                    .append(getTable())
                    .append(" WHERE ")
                    .append(Arrays
                            .stream(getWhereConditions())
                            .map(c -> c.name() + c.operator() + "?")
                            .collect(Collectors.joining(" AND ")));
        }
        return sb.toString();
    }

}
