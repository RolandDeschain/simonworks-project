/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion;

import org.simonworks.projects.reflection.Typed;

/**
 * This interface's instances represents a deserialization process (or transformation) from a {@link String}
 * to a generic type.
 *

 */
public interface Deserializer {

    /**
     * Deserializes the input {@link String} into an object identified by input {@link Class} parameter.
     *
     * @param input
     *  Source String to transform.
     * @param clazz
     *  The class of the output object to produce.
     * @param <O>
     *     The generic type this {@link Deserializer} is able to produce.
     * @return
     *  An instance of the {@link Class} provided in input.
     * @throws DeserializationException
     *  If any exception occurs during this process.
     */
    default <O> O deserialize(String input, Class<O> clazz) throws DeserializationException {
        return deserialize(input, new Typed<>(clazz));
    }

    /**
     * Deserializes the input {@link String} into an object identified by input {@link Class} parameter.
     *
     * @param input
     *  Source String to transform.
     * @param typed
     *  The typed that represents the type of the instance to produce
     * @param <T>
     *     The generic type this {@link Deserializer} is able to produce.
     * @return
     *  An instance of the {@link Class} provided in input.
     * @throws DeserializationException
     *  If any exception occurs during this process.
     */
    <T> T deserialize(String input, Typed<T> typed) throws DeserializationException;

}
