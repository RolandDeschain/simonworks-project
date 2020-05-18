/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.factory;

import org.simonworks.projects.annotations.Configurable;
import org.simonworks.projects.configurations.ClasspathPropertiesFileConfiguration;
import org.simonworks.projects.configurations.Configuration;

@Configurable(
        configurationFile = "not-existing.properties",
        configurationClass = ClasspathPropertiesFileConfiguration.class,
        configurationMethod = "config"
)
public class ConfigurationExceptionExample {

    public void config(Configuration c) {

    }
}
