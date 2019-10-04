/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.factory.BeanFactory;
import org.simonworks.projects.factory.DefaultBeanFactory;

import java.util.UUID;

abstract class AbstractApplicationContext implements ApplicationContext {

    private String id = UUID.randomUUID().toString();

    private String name = "applicationContext";

    private BeanFactory beanFactory;

    private BeanRegistry beanRegistry;

    private SingletonsCache singletonsCache;

    AbstractApplicationContext(BeanRegistryProvider beanRegistryProvider) {
        this(
                new DefaultBeanFactory(),
                beanRegistryProvider,
                new DefaultSingletonsCache());
    }

    AbstractApplicationContext(BeanFactory beanFactory,
                                      BeanRegistryProvider beanRegistryProvider,
                                      SingletonsCache singletonsCache) {
        setBeanFactory(beanFactory);
        setBeanRegistryProvider(beanRegistryProvider);
        setSingletonsCache(singletonsCache);
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    BeanFactory getBeanFactory() {
        return beanFactory;
    }

    private void setBeanFactory(BeanFactory beanFactory) {
        assert beanFactory != null : "Cannot set null bean factory on application context";
        this.beanFactory = beanFactory;
    }

    BeanRegistry getBeanRegistry() {
        return beanRegistry;
    }

    private void setBeanRegistryProvider(BeanRegistryProvider beanRegistryProvider) {
        assert beanRegistryProvider != null : "Bean registry provider cannot be null";
        beanRegistry = beanRegistryProvider.loadBeanRegistry();
    }

    SingletonsCache getSingletonsCache() {
        return singletonsCache;
    }

    private void setSingletonsCache(SingletonsCache singletonsCache) {
        assert singletonsCache != null : "Cannot set null singletons cache on application context";
        this.singletonsCache = singletonsCache;
    }
}
