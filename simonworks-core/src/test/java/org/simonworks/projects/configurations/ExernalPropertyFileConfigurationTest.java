/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.configurations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExernalPropertyFileConfigurationTest {

    @Test
    void openInputStream() {
        Assertions.assertThrows(ConfigurationException.class,
                () -> new ExernalPropertyFileConfiguration("not-existing.properties"));


    }
}