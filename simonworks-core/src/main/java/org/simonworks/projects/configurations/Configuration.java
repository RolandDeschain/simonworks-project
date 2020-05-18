/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.configurations;

import static org.simonworks.projects.utils.Assertions.assertTrue;

public interface Configuration {

    boolean containsConfig(String configName);

    Object get(String configName);

    default String getString(String configName) {
        assertTrue(this.containsConfig(configName), "Configuration <" + configName + "> not exists!");
        return String.valueOf(
                this.get(configName));
    }

    default Integer getInt(String configName) {
        return Integer.valueOf(
                this.getString(configName));
    }

    default Double getDouble(String configName) {
        return Double.valueOf(
                this.getString(configName));
    }

    default Boolean getBoolean(String configName) {
        return Boolean.valueOf(
                this.getString(configName));
    }
}
