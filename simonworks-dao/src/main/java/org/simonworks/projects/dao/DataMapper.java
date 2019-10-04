/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao;

import java.util.function.Function;

@FunctionalInterface
public interface DataMapper<I, O> extends Function<I, O> {

    @Override
    default O apply(I i) {
        return map(i);
    }

    O map(I i);
}
