/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.domain;

import java.util.function.Function;

@FunctionalInterface
/**
 * Object that maps (converts) and Object into another
 */
public interface DataMapper<I, O> extends Function<I, O> {

    @Override
    default O apply(I i) {
        return map(i);
    }

    O map(I i);

}
