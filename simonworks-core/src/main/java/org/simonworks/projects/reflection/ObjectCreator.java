/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.reflection;

import java.util.function.Function;

/**
 * An Object creator whose aim is to create a default instance of an object.
 * @param <T>
 *           The type of the Object to create.
 */
@FunctionalInterface
public interface ObjectCreator<T> extends Function<Class<T>, T> {

    default T create(Class<T> classOf) {
        return apply(classOf);
    }
}
