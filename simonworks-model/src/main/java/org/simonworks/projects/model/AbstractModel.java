/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.model;

import org.simonworks.projects.utils.Assertions;

import java.util.Objects;

public abstract class AbstractModel<I> implements Model<I> {

    private I id;

    public AbstractModel() {}

    public AbstractModel(I id) {
        setId(id);
    }

    @Override
    public I getId() {
        return id;
    }

    public void setId(I id) {
        Assertions.assertNotNull(id, "Id cannot be null!");
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractModel<?> that = (AbstractModel<?>) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
