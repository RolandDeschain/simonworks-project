/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

public class MapBeanRegistryProvider extends AbstractClasspathBeanRegistryProvider {

    public MapBeanRegistryProvider(String ... packagesToScan) {
        super(packagesToScan);
    }

    @Override
    public BeanRegistry createEmptyBeanRegistry() {
        return new MapBeanRegistry();
    }
}
