/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

class DefaultSingletonsCache implements SingletonsCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSingletonsCache.class);
    private final Map<String, Object> cache = new ConcurrentHashMap<>(256);

    @Override
    public Object get(String name) {
        Object result = cache.get(name);
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Getting bean named <{}> from cache : {}", name, result);
        }
        return result;
    }

    @Override
    public void put(String name, Object obj) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Storing object <{}> result with alias <{}>", obj, name);
        }
        cache.put(name, obj);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultSingletonsCache.class.getSimpleName() + "[", "]")
                .add("cache=" + cache)
                .toString();
    }
}
