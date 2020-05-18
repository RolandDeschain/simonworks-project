/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Configurable;
import org.simonworks.projects.annotations.PostConstructor;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.configurations.ClasspathPropertiesFileConfiguration;
import org.simonworks.projects.configurations.Configuration;
import org.simonworks.projects.context.annotation.InjectBeanContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configurable(
        configurationFile = "example-singleton.properties",
        configurationClass = ClasspathPropertiesFileConfiguration.class,
        configurationMethod = "config"
)
@Singleton
public class ExampleSingleton {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExamplePrototype.class);

    int a;
    boolean postConstructInvoked;
    boolean configInvoked;

    private String connection;

    @InjectBeanContext
    private BeanContext beanContext;

    @PostConstructor
    public void postConstruct() {
        postConstructInvoked = true;
    }

    public void config(Configuration configuration) {
        configInvoked = true;
        a = configuration.getInt("a");
        connection = configuration.getString("connection");
    }

    void print() {
        LOGGER.info(connection);
    }

    public String getConnection() {
        return connection;
    }

    BeanContext getBeanContext() {
        return beanContext;
    }
}
