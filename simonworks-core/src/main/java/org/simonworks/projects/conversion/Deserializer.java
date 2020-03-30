/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion;

public interface Deserializer<I, O> {

    O deserialize(I input, Class<O> clazz) throws DeserializationException;

}
