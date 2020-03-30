/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.domain;

public interface Pageable {

    int getPageNumber();

    int getPageSize();

    default long getOffset() {
        return (long) this.getPageNumber() * this.getPageSize();
    }

    Pageable next();

    default boolean hasPrevious() {
        return getPageNumber() > 0;
    }

    default Pageable previous() { return hasPrevious() ? of(getPageNumber() - 1, getPageSize(), getSort()): this; }

    Sort getSort();

    Pageable of(int pageNumber, int pageSize, Sort sort);

}
