/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import org.simonworks.projects.dao.domain.Pageable;
import org.simonworks.projects.dao.query.Query;
import org.simonworks.projects.model.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface JdbcDaoSupport<O extends Model<ID>, ID> extends PagingAndSortingDao<ResultSet, O, ID> {

    @Override
    default long count() {
        try (Connection c = getDBService().getConnection();
             Statement statement = c.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(
                    Query.select().count().columns("*").from(getTargetDataContainer()).where().buildQuery()
            );
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    default long delete(ID id) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.delete().from(getTargetDataContainer()).where(idColumns()).buildQuery())
        ) {
            ps.setLong(1, Long.parseLong(String.valueOf(id)));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default long deleteAll() {
        try (Connection c = getDBService().getConnection();
             Statement ps = c.createStatement()
        ) {
            ResultSet resultSet = ps.executeQuery(
                    Query.delete().from(getTargetDataContainer()).where().buildQuery()
            );
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default O findById(ID id) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(
                     Query.select().columns(entityColumns()).from(getTargetDataContainer()).where("ID").buildQuery()
             )
        ) {
            ps.setLong(1, Long.parseLong(String.valueOf(id)));
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return getEntityToModelDataMapper().map(resultSet);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default Iterable<O> findAllByIds(Iterable<ID> iterable) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(getFindAllByIds())) {
            ps.setObject(1, StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList()));
            ResultSet resultSet = ps.executeQuery();
            List<O> result = new ArrayList<>();
            if (resultSet.next()) {
                result.add(getEntityToModelDataMapper().map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default long update(ID id, O o) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(getUpdateSQL())) {
            fillStatement(ps, o);
            ps.setObject(4, Long.valueOf(String.valueOf(id)));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default long updateAll(Iterable<ID> iterable, O o) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement ps = c.prepareStatement(getUpdateAllSQL())) {
            fillStatement(ps, o);
            ps.setObject(4, StreamSupport
                    .stream(iterable.spliterator(), false)
                    .collect(Collectors.toList()));
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    default List<O> findPage(Pageable pageable) {
        try (Connection c = getDBService().getConnection();
             PreparedStatement preparedStatement = c.prepareStatement(getFindPageSQL() + getHandlePageableSQL())) {
            preparedStatement.setLong(1, pageable.getOffset());
            preparedStatement.setInt(2, pageable.getPageSize());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<O> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getEntityToModelDataMapper().map(resultSet));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void fillStatement(PreparedStatement ps, O o);

    String[] entityColumns();

    String[] idColumns();

    String getSaveSQL();

    String getDeleteOneSQL();

    String getDeleteAllSQL();

    String getFindByIdSQL();

    String getFindAllByIds();

    String getUpdateSQL();

    String getUpdateAllSQL();

    String getFindPageSQL();

    String getHandlePageableSQL();
}
