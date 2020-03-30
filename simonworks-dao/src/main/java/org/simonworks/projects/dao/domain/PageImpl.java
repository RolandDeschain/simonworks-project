/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.dao.domain;

public class PageImpl extends AbstractPageable {

    public PageImpl(int pageNumber, int size, Sort sort) {
        super(pageNumber, size, sort);
    }

    @Override
    public Pageable next() {
        return new PageImpl(getPageNumber() + 1, getPageSize(), getSort());
    }

    @Override
    public Pageable of(int pageNumber, int pageSize, Sort sort) {
        return new PageImpl(pageNumber, pageSize, sort);
    }
}
