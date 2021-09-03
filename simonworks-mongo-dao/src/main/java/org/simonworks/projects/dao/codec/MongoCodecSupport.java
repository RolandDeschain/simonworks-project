/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.dao.codec;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import org.simonworks.projects.model.Model;
import org.simonworks.projects.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface MongoCodecSupport<O> extends Codec<O> {

    default String readString(BsonReader r, String name) {
        Document d;
        r.readName(name);
        return r.readString();
    }

    default double readDouble(BsonReader r, String name) {
        r.readName(name);
        return r.readDouble();
    }

    default int readInt(BsonReader r, String name) {
        r.readName(name);
        return r.readInt32();
    }

    default <E> List<E> readArray(BsonReader r, MongoCodecSupport<E> codec, DecoderContext context, String name) {
        r.readName(name);
        r.readStartArray();
        List<E> result = new ArrayList<>();
        while(r.readBsonType() != BsonType.END_OF_DOCUMENT) {
            result.add( codec.decode(r, context) );
        }
        r.readEndArray();
        return result;
    }

    default <E> void writeArray(BsonWriter w, Iterable<E> iterable, MongoCodecSupport<E> codec, EncoderContext context, String name) {
        w.writeName(name);
        w.writeStartArray();
        iterable.forEach(e -> codec.encode(w, e, context));
        w.writeEndArray();
    }

    default void writeId(Model<String> model, BsonWriter writer) {
        if(StringUtils.isNotEmpty(model.getId())) {
            writer.writeObjectId("_id", new ObjectId(model.getId()));
        }
    }

    default String readId(BsonReader bsonReader) {
        String name = bsonReader.readName();
        return "_id".equals(name) ? bsonReader.readObjectId().toString() : null;
    }
}
