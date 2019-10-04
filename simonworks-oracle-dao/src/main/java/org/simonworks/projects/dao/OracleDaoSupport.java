/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.model.Model;

public interface OracleDaoSupport<O extends Model<ID>, ID> extends JdbcDaoSupport<O, ID> {

    @Override
    default String getHandlePageableSQL() {
        return " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    }
}
