/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.simonworks.projects.annotations.Configurable;
import org.simonworks.projects.annotations.PostConstructor;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.configurations.ClasspathPropertiesFileConfiguration;
import org.simonworks.projects.configurations.Configuration;

@Singleton
@Configurable(
        configurationFile = "example-test.properties",
        configurationClass = ClasspathPropertiesFileConfiguration.class,
        configurationMethod = "config"
)
class Example {

    int a;
    boolean postConstructInvoked;
    boolean configInvoked;

    @PostConstructor
    public void postConstruct() {
        postConstructInvoked = true;
    }

    public void config(Configuration configuration) {
        configInvoked = true;
        a = configuration.getInt("a");
    }
}
