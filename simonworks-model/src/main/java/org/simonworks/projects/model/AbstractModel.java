/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.model;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

public abstract class AbstractModel<I> implements Model<I> {

    private I id;

    AbstractModel() {}

    public AbstractModel(I id) {
        assertNotNull(id, "Id cannot be null!");
        this.id = id;
    }

    @Override
    public I getId() {
        return id;
    }

}
