/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.model.Model;

import java.io.Closeable;
import java.io.IOException;

public interface DaoSupport<O /*Model*/ extends Model<I>, I> extends Closeable {

    String getTargetDataContainer();

    long count();

    void save(O o);

    long delete(I id);

    long deleteAll();

    O findById(I id);

    Iterable<O> findAllByIds(Iterable<I> ids);

    long update(I id, O o);

    long updateAll(Iterable<I> ids, O o);

    DbService getDBService();

    @Override
    default void close() throws IOException {
        getDBService().close();
    }

    Class<O> getManagedOutputClass();

}
