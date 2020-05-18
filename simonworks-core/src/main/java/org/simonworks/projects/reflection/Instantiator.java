/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package org.simonworks.projects.reflection;

import java.lang.reflect.Type;
import java.util.function.Function;

/**
 * A {@link java.util.function.Function} of instances that don't provide a default constructor. Instances should be fresh created.
 * @param <T>
 *     The generic type of the created instances
 */
@FunctionalInterface
public interface Instantiator<T> extends Function<Type, T> {

    default T create(Type type) {
        return apply(type);
    }
}
