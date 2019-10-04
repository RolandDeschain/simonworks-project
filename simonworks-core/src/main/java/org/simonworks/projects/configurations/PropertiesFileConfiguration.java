/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.configurations;

import org.simonworks.projects.utils.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

public abstract class PropertiesFileConfiguration implements Configuration {

    private static Logger LOGGER = LoggerFactory.getLogger(PropertiesFileConfiguration.class);

    private Properties properties = new Properties();

    public PropertiesFileConfiguration(Properties properties) {
        assertNotNull(properties, "Cannot inherit from null properties");
        this.properties = properties;
    }

    public PropertiesFileConfiguration(String filename) {
        try (InputStream inputStream = this.openInputStream(filename)) {
            this.properties.load(inputStream);
        } catch (IOException | NullPointerException e) {
            LOGGER.error("Can't load configuration properties from file <{}>", filename, e);
            throw new ConfigurationException("Can't load configuration properties from file " + filename, e);
        }
    }

    public abstract InputStream openInputStream(String filename) throws IOException;

    @Override
    public boolean containsConfig(String configName) {
        return this.properties.containsKey(configName);
    }

    @Override
    public Object get(String configName) {
        return this.properties.getProperty(configName);
    }
}
