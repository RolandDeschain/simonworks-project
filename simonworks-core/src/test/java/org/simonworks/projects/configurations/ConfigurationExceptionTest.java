/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.configurations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigurationExceptionTest {

    @Test public void testConstructor() {
        ConfigurationException ce = new ConfigurationException("test", new RuntimeException());
        Assertions.assertEquals("test", ce.getMessage());
        Assertions.assertEquals(RuntimeException.class, ce.getCause().getClass());
    }

}