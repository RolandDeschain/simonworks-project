/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.database.DbService;
import org.simonworks.projects.model.Model;

import java.io.Closeable;
import java.io.IOException;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

public interface DaoSupport<I /*Entity*/, O /*Model*/ extends Model<ID>, ID> extends Closeable {

    String getTargetDataContainer();

    long count();

    void save(O o);

    long delete(ID id);

    long deleteAll();

    O findById(ID id);

    Iterable<O> findAllByIds(Iterable<ID> ids);

    long update(ID id, O o);

    long updateAll(Iterable<ID> ids, O o);

    <C> void setDBService(DbService dbService);

    <C> DbService getDBService();

    @Override
    default void close() throws IOException {
        getDBService().close();
    }

    DataMapper<I, O> getEntityToModelDataMapper();

}
