/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.configurations;

import java.io.IOException;
import java.io.InputStream;

import static java.util.Objects.requireNonNull;

public class ClasspathPropertiesFileConfiguration extends PropertiesFileConfiguration {

    public ClasspathPropertiesFileConfiguration(String filename) {
        super(filename);
    }

    @Override
    public InputStream openInputStream(String filename) throws IOException {
        return requireNonNull(
                getClass()
                        .getClassLoader()
                        .getResource(filename))
                .openStream();
    }
}
