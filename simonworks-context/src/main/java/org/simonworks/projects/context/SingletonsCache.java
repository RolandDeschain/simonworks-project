package org.simonworks.projects.context;

public interface SingletonsCache {

    Object get(String name);

    void put(String name, Object obj);
}
