/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.domain;

public abstract class AbstractPageable implements Pageable {

    private int pageNumber;
    private int size;
    private Sort sort;

    AbstractPageable(int pageNumber, int size, Sort sort) {
        assert pageNumber >= 0 : "Page number cannot be lesser than zero!";
        this.pageNumber = pageNumber;
        assert size > 0 : "Page size cannot be lesser than or equal zero!";
        this.size = size;
        this.sort = sort == null ? Sort.get() : sort;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

}
