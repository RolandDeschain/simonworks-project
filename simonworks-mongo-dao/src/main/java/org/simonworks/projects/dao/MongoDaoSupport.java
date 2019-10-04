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
import org.simonworks.projects.model.Model;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface MongoDaoSupport<O extends Model<ID>, ID>
        extends PagingAndSortingDao<Document, O, ID> {
    
    default MongoCollection<Document> getCollection() {
        MongoDatabase database = getDBService().getConnection();
        return database.getCollection( getTargetDataContainer() );
    }

    Bson getFilterById(ID id);

    Bson getFilterByIds(Iterable<ID> ids);

    DataMapper<O, Document> getModelToEntityDataMapper();

    @Override
    default long count() {
       return getCollection().countDocuments();
    }

    default long count(Bson filter) {
        return getCollection().countDocuments(filter);
    }

    @Override
    default void save(O o) {
        Document toSave = getModelToEntityDataMapper().map(o);
        getCollection().insertOne(toSave);
    }

    @Override
    default long delete(ID id) {
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
    default O findById(ID id) {
        Bson query = getFilterById(id);
        Document result = getCollection().find(query).first();
        assert result != null : "No Document found for id " + id;
        return getEntityToModelDataMapper().map(result);
    }

    @Override
    default Iterable<O> findAllByIds(Iterable<ID> iterable) {
        Bson query = getFilterByIds(iterable);
        return StreamSupport
                .stream(
                        getCollection().find(query).spliterator(), false)
                .map(getEntityToModelDataMapper())
                .collect(Collectors.toList());
    }

    @Override
    default long update(ID id, O o) {
        Bson query = getFilterById(id);
        Bson update = new Document("$set", getModelToEntityDataMapper().map(o) );
        UpdateResult updateResult = getCollection().updateOne(query, update);
        return updateResult.getModifiedCount();
    }

    @Override
    default long updateAll(Iterable<ID> iterable, O o) {
        Bson query = getFilterByIds(iterable);
        Bson update = new Document("$set", getModelToEntityDataMapper().map(o) );
        UpdateResult updateResult = getCollection().updateMany(query, update);
        return updateResult.getModifiedCount();
    }

    @Override
    default List<O> findPage(Pageable pageable) {
        return StreamSupport
                .stream(getCollection()
                        .find()
                        .skip((int) pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .spliterator(), false)
                .map(getEntityToModelDataMapper())
                .collect(Collectors.toList());
    }

}
