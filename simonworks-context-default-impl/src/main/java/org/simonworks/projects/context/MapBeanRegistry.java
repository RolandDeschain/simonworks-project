/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.reflection.Typed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

class MapBeanRegistry implements BeanRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapBeanRegistry.class);

    private Map<String, BeanInfo> registry = new ConcurrentHashMap<>(256);

    @Override
    public void registerBean(Typed<?> type) {
        if(isDiscoverableClass(type)) {
            BeanInfo info = createBeanInfo(type);
            assertNotNull(info, "Cannot register null bean info");
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Registering aliases {} for bean info <{}>", info.aliases(), info);
            }
            info.aliases().forEach(s -> registry.put(s, info));
        }
    }

    protected boolean isDiscoverableClass(Typed<?> type) {
        return type.isAnnotationPresent(Prototype.class) || type.isAnnotationPresent(Singleton.class);
    }

    public BeanInfo createBeanInfo(Typed<?> type) {
        return new BeanInfo(type);
    }

    @Override
    public void unregisterBean(String name) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Unregistering bean info for name <{}>", name);
        }
        registry.remove(name);
    }

    @Override
    public BeanInfo getBeanInfo(String name) {
        BeanInfo result = registry.get(name);
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("getBeanInfo({})={}", name, result);
        }
        return result;
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", getClass().getSimpleName() + "[", "]");
        for(Map.Entry<String, BeanInfo> entry : registry.entrySet()) {
            sj.add("\n" + entry.getKey() + " = " + entry.getValue());
        }
        return sj.toString();
    }

    @Override
    public Set<String> beanNamesSet() {
        return registry.keySet();
    }
}
