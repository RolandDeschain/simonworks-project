package org.simonworks.projects.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class DefaultSingletonsCache implements SingletonsCache {

    private final Map<String, Object> cache = new ConcurrentHashMap<>(256);


    @Override
    public Object get(String name) {
        return cache.get(name);
    }

    @Override
    public void put(String name, Object obj) {
        cache.put(name, obj);
    }
}
