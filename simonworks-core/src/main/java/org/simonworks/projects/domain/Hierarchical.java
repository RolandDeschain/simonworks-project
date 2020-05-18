/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

/**
 * Interface that defines hierarchical objects
 */
public interface Hierarchical<T> {

    T getParent();

    T getNoopParent();

    void setParent(T hierarchical);
}
