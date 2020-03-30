/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import java.util.Optional;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

/**
 * Abstract hierarchical implementation
 * @param <T>
 */
public abstract class AbstractHierarchical<T> implements Hierarchical<T> {

    private T parent;

    @Override
    public T getParent() {
        return Optional.ofNullable(parent).orElse(getNoopParent());
    }

    @Override
    public void setParent(T parent) {
        assertNotNull(parent, "Parent Object cannot be null");
        this.parent = parent;
    }
}
