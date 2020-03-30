/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.model;

/**
 * Builder interface for new object of the type specified by generic argument <em>T</em>
 * @param <T>
 *     The generic of the instances created by this Builder
 */
public interface Builder<T> {

    /**
     * Creates and return a new instance of type <em>T</em>
     * @return
     *  A new instance of type <em>T</em>
     */
    T build();

    /**
     * Return itself
     * @return
     *  Itself
     */
    Builder<T> self();
}
