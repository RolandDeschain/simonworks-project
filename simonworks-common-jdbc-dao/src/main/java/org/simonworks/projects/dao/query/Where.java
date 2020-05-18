/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;
import java.util.stream.Collectors;

@FunctionalInterface
public interface Where extends Clause {

    default QueryComplete where(String ... columns) {
        return where(Arrays
                .stream(columns)
                .map(Column::eq)
                .collect(Collectors.toList())
                .toArray(new Column[0]));
    }

    default QueryComplete where() {
        return where(Const.EMPTY);
    }

    QueryComplete where(Column ... columns);
}
