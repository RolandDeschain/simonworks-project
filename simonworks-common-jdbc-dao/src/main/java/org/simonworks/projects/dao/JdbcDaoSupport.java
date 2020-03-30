/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.dao.domain.Pageable;
import org.simonworks.projects.dao.query.Column;
import org.simonworks.projects.dao.query.Query;
import org.simonworks.projects.domain.DataMapper;
import org.simonworks.projects.model.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface JdbcDaoSupport<O extends Model<I>, I> extends PagingAndSortingDao<O, I> {

    DataMapper<ResultSet, O> getEntityToModelDataMapper();

    @Override
    default long count() {
        try (Connection c = getDBService().getConnection();
             Statement statement = c.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     Query.select().count().columns("*").from(getTargetDataContainer()).where().buildQuery()
            );
        ) {
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default void save(O o) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.insert().into(getTargetDataContainer()).columns(entityColumns()).buildQuery()
             )
        ) {
            fillStatement(ps, o);
            ps.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default long delete(I id) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.delete().from(getTargetDataContainer()).where(idColumns()).buildQuery())
        ) {
            ps.setLong(1, Long.parseLong(String.valueOf(id)));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default long deleteAll() {
        try (Connection c = getDBService().getConnection();
             Statement ps = c.createStatement();
             ResultSet resultSet = ps.executeQuery(
                     Query.delete().from(getTargetDataContainer()).where().buildQuery()
             );
        ) {
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default O findById(I id) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.select().columns(entityColumns()).from(getTargetDataContainer()).where("ID").buildQuery()
             );
        ) {
            ps.setLong(1, Long.parseLong(String.valueOf(id)));
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getEntityToModelDataMapper().map(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default Iterable<O> findAllByIds(Iterable<I> iterable) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                Query.select().columns(entityColumns()).from(getTargetDataContainer()).where(Column.in("ID")).buildQuery()
             )
        ) {
            ps.setObject(1, StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList()));
            List<O> result = new ArrayList<>();
            try(ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    result.add(getEntityToModelDataMapper().map(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default long update(I id, O o) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.update().table(getTargetDataContainer()).set(entityColumns()).where("ID").buildQuery()
             )
        ) {
            fillStatement(ps, o);
            ps.setObject(4, Long.parseLong(String.valueOf(id)));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default long updateAll(Iterable<I> iterable, O o) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.update().table(getTargetDataContainer()).set(entityColumns()).where(Column.in("ID")).buildQuery()
             )
        ) {
            fillStatement(ps, o);
            ps.setObject(4, StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    default List<O> findPage(Pageable pageable) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(
                     Query.select().columns(entityColumns()).from(getTargetDataContainer()).where().buildQuery() + getHandlePageableSQL())
        ) {
            preparedStatement.setLong(1, pageable.getOffset());
            preparedStatement.setInt(2, pageable.getPageSize());
            List<O> result = new ArrayList<>();
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(getEntityToModelDataMapper().map(resultSet));
                }
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    void fillStatement(PreparedStatement ps, O o);

    String[] entityColumns();

    String[] idColumns();

    String getHandlePageableSQL();
}
