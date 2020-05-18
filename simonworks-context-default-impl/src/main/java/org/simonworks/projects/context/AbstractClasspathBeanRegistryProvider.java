/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClasspathBeanRegistryProvider implements InitializableBeanRegistryProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractClasspathBeanRegistryProvider.class);

    private List<String> packagesToScan = new ArrayList<>();

    public AbstractClasspathBeanRegistryProvider(String ... packagesToScan) {
        for(String p : packagesToScan) {
            addPackageToScan(p);
        }
    }

    public void addPackageToScan(String packageToScan) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Adding package <{}> in autoscan-bean list", packageToScan);
        }
        packagesToScan.add(packageToScan);
    }

    @Override
    public BeanRegistry loadBeanRegistry() {
        BeanRegistry registry = createEmptyBeanRegistry();
        packagesToScan
                .forEach(packageName -> {
                    try {
                        if(LOGGER.isInfoEnabled()) {
                            LOGGER.info("Scanning package <{}> for beans", packageName);
                        }
                        ClassUtils
                                .getClasses(packageName)
                                .forEach(registry::registerBean);
                    } catch (ClassNotFoundException | IOException | URISyntaxException e) {
                        LOGGER.error("Cannot process package <" + packageName + ">", e);
                    }
                });
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Ready to use bean registry <{}>", registry);
        }
        return registry;
    }
}
