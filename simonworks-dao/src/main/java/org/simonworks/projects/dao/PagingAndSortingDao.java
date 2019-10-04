/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.dao.domain.Pageable;
import org.simonworks.projects.model.Model;

import java.util.List;

public interface PagingAndSortingDao<I, O extends Model<ID>, ID> extends DaoSupport<I, O, ID> {

    List<O> findPage(Pageable pageable);
}
