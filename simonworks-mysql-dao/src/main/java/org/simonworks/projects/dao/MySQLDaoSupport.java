/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.model.Model;

public interface MySQLDaoSupport<O extends Model<I>, I> extends JdbcDaoSupport<O, I> {

    @Override
    default String getHandlePageableSQL() {
        return " LIMIT ?, ?";
    }
}