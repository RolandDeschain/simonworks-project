/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion;

import org.simonworks.projects.domain.DataMapper;

/**
 * Object able to serialize an Object into a String
 * @param <I>
 *     The type of Object to serialize
 */
public interface Serializer<I> extends DataMapper<I, String> {

    String serialize(I input);

    @Override
    default String map(I i) {
        return serialize(i);
    }
}
