/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.simonworks.projects.dao.domain.Pageable;
import org.simonworks.projects.domain.DataMapper;
import org.simonworks.projects.model.Model;
import org.simonworks.projects.utils.Assertions;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface MongoDaoSupport<O extends Model<I>, I>
        extends PagingAndSortingDao<O, I> {

    Bson getFilterById(I id);

    Bson getFilterByIds(Iterable<I> ids);

    DataMapper<O, Document> getModelToEntityDataMapper();

    default MongoCollection<O> getCollection() {
        MongoDatabase database = getDBService().getConnection();
        return database.getCollection( getTargetDataContainer(), getManagedOutputClass() );
    }

    @Override
    default long count() {
       return getCollection().countDocuments();
    }

    default long count(Bson filter) {
        return getCollection().countDocuments(filter);
    }

    @Override
    default void save(O o) {
        getCollection().insertOne(o);
    }

    @Override
    default long delete(I id) {
        Bson query = getFilterById(id);
        DeleteResult deleteResult = getCollection().deleteOne(query);
        return deleteResult.getDeletedCount();
    }

    @Override
    default long deleteAll() {
        long count = getCollection().countDocuments();
        getCollection().drop();
        return count;
    }

    @Override
    default O findById(I id) {
        Bson query = getFilterById(id);
        O result = getCollection().find(query, getManagedOutputClass()).first();
        Assertions.assertNotNull(result, "No Document found for id " + id);
        return result;
    }

    @Override
    default Iterable<O> findAllByIds(Iterable<I> iterable) {
        Bson query = getFilterByIds(iterable);
        return StreamSupport
                .stream(
                        getCollection().find(query, getManagedOutputClass()).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    default long update(I id, O o) {
        Bson query = getFilterById(id);
        // removing _id field from update -> prevents update error
        Document entity = getModelToEntityDataMapper().map(o);
        entity.remove("_id");
        Bson update = new Document("$set",  entity);
        UpdateResult updateResult = getCollection().updateOne(query, update);
        return updateResult.getModifiedCount();
    }

    @Override
    default long updateAll(Iterable<I> iterable, O o) {
        Bson query = getFilterByIds(iterable);
        // removing _id field from update -> prevents update error
        Document entity = getModelToEntityDataMapper().map(o);
        entity.remove("_id");
        Bson update = new Document("$set",  entity);
        UpdateResult updateResult = getCollection().updateMany(query, update);
        return updateResult.getModifiedCount();
    }

    @Override
    default List<O> findPage(Pageable pageable) {
        return StreamSupport
                .stream(getCollection()
                        .find(getManagedOutputClass())
                        .skip((int) pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .spliterator(), false)
                .collect(Collectors.toList());
    }

}
