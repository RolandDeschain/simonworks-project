/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.model;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

public abstract class AbstractModel<ID> implements Model<ID> {

    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {

        assertNotNull(id, "Id cannot be null!");

        this.id = id;
    }

}
