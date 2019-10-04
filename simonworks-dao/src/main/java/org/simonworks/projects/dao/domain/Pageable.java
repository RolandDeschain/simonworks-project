/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.domain;

public interface Pageable {

    int getPageNumber();

    int getPageSize();

    default long getOffset() {
        return this.getPageNumber() * this.getPageSize();
    }

    Pageable next();

    default boolean hasPrevious() {
        return getPageNumber() > 0;
    }

    Pageable previous();

    Sort getSort();

}
