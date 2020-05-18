/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Configurable;
import org.simonworks.projects.annotations.PostConstructor;
import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.configurations.ClasspathPropertiesFileConfiguration;
import org.simonworks.projects.configurations.Configuration;
import org.simonworks.projects.context.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configurable(
        configurationFile = "example-prototype.properties",
        configurationClass = ClasspathPropertiesFileConfiguration.class,
        configurationMethod = "config"
)
@Prototype(name = "myTestPrototype")
public class ExamplePrototype {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamplePrototype.class);

    @Dependency(beanName = "example-singleton")
    private Object testServiceSingleton;

    @PostConstructor
    public void postConstruct() {
        boolean postConstructInvoked = true;
    }

    public void config(Configuration configuration) {
        boolean configInvoked = true;
        int a = configuration.getInt("a");
    }

    void doSomething() {
        LOGGER.info("doSomething()");
        ((ExampleSingleton) testServiceSingleton).print();
    }

    Object getTestServiceSingleton() {
        return testServiceSingleton;
    }
}