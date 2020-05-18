/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.configurations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ExernalPropertyFileConfiguration extends PropertiesFileConfiguration {

    public ExernalPropertyFileConfiguration(String filename) {
        super(filename);
    }

    @Override
    public InputStream openInputStream(String filename) throws IOException {
        return new FileInputStream(filename);
    }
}
