/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.model;

import org.junit.jupiter.api.Test;
import org.simonworks.projects.utils.AssertionError;

import static org.junit.jupiter.api.Assertions.*;

class AbstractModelTest {

    @Test
    void get_set_Id() {
        Model<String> m = new AbstractModel<String>("id1") {};

        assertNotNull(m.getId());
        assertEquals("id1", m.getId());
    }

    @Test void setNullId_thenException() {
        assertThrows(AssertionError.class,
                () -> new AbstractModel<String>(null) {} );
    }
}