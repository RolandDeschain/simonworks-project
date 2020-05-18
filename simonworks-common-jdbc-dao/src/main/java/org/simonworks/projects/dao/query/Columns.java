/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface Columns<T> {
    default T columns(String ... columns) {
        return columns(Arrays
                .stream(columns)
                .map(Column::eq)
                .collect(Collectors.toList())
                .toArray(new Column[0]));
    }
    T columns(Column ... columns);
}
