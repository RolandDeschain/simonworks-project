/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.reflection.ReflectionSupport;
import org.simonworks.projects.factory.BeanFactory;
import org.simonworks.projects.factory.DefaultBeanFactory;
import org.simonworks.projects.utils.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

abstract class AbstractBeanContext implements BeanContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBeanContext.class);
    private String id = UUID.randomUUID().toString();
    private String name = "applicationContext";
    private BeanFactory beanFactory = new DefaultBeanFactory();
    private BeanRegistry beanRegistry;
    private SingletonsCache singletonsCache = new DefaultSingletonsCache();

    public AbstractBeanContext(BeanRegistry beanRegistry) {
        setBeanRegistry(beanRegistry);
    }

    @Override
    public <T> T getBean(String name) {
        Object result;
        // First recover bean info
        BeanInfo beanInfo = getBeanInfo(name);
        switch(beanInfo.getLifecycle()) {
            default: case PROTOTYPE:
                result = processPrototype(beanInfo);
                break;
            case SINGLETON:
                result = processSingleton(beanInfo);
                break;
        }
        handleBeanAnnotations(result, beanInfo);
        return (T) result;
    }

    private Object processSingleton(BeanInfo beanInfo) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("processSingleton(BeanInfo {})", beanInfo.aliases());
        }
        Object result;/*
         * Checking singletons cache for a ready to use instance
         */
        result = get( beanInfo );
        if( result == null ) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("No singleton bean available in cache for names <{}>",  beanInfo.aliases() );
            }
            /*
             * Create the only singletons instance
             */
            result = getBeanFactory().create(beanInfo.getType());
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Created singleton bean <{}> with aliases <{}>",
                        result, beanInfo.aliases() );
            }
            /*
             * Register singleton
             */
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Registering singleton bean <{}> in singleton cache with aliases <{}>",
                        result, beanInfo.aliases() );
            }
            store(result, beanInfo);
        }
        return result;
    }

    private Object processPrototype(BeanInfo beanInfo) {
        Object result;/*
         * Simply create a new instance
         */
        result = getBeanFactory().create(beanInfo.getType());
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Created prototype bean <{}> from using info <{}>", result, beanInfo);
        }
        return result;
    }

    protected BeanInfo getBeanInfo(String name) {
        return getBeanRegistry().getBeanInfo(name);
    }

    public void handleBeanAnnotations(Object bean, BeanInfo beanInfo) {
        doHandleBeanAnnotations(bean, beanInfo);
    }

    protected abstract void doHandleBeanAnnotations(Object bean, BeanInfo beanInfo);

    protected void checkRequiredBeanContext(Object result, Field injectableBeanContext) {
        if(injectableBeanContext != null) {
            try {
                ReflectionSupport.writeValue(result, injectableBeanContext.getName(), self());
            } catch (Exception e) {
                LOGGER.error("Exception while invoking checkRequiredApplicationContext(Object <{}>, Field <{}>",
                        result, injectableBeanContext, e);
            }
        }
    }

    protected Object get(BeanInfo beanInfo) {
        Object result = searchInCache(beanInfo, getSingletonsCache());
        if(LOGGER.isDebugEnabled()) {
            if(result == null) {
                LOGGER.debug("Context {} doesn't contains bean definition for bean info <{}>.\n\tLocal bean recovery returned <{}> ", getId(), beanInfo, result);
            } else {
                LOGGER.debug("Get bean from context {}, using bean info {}, returned <{}>", getId(), beanInfo, result);
            }
        }
        return result;
    }

    protected Object searchInCache(BeanInfo beanInfo, SingletonsCache cache) {
        AtomicReference<Object> atomic = new AtomicReference<>();
        beanInfo.aliases().forEach(alias -> atomic.set(cache.get(alias)));
        return atomic.get();
    }

    protected void store(Object result, BeanInfo beanInfo) {
        beanInfo.aliases().forEach(alias -> getSingletonsCache().put(alias , result));
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

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        Assertions.assertNotNull(beanFactory, "Cannot set null bean factory on application context");
        this.beanFactory = beanFactory;
    }

    public BeanRegistry getBeanRegistry() {
        return Optional.ofNullable(beanRegistry).orElse(NoopBeanRegistry.get());
    }

    public void setBeanRegistry(BeanRegistry beanRegistry) {
        Assertions.assertNotNull(beanRegistry, "Bean registry cannot be null");
        this.beanRegistry = beanRegistry;
    }

    public SingletonsCache getSingletonsCache() {
        return Optional.ofNullable(singletonsCache).orElse(NoopSingletonCache.get());
    }

    public void setSingletonsCache(SingletonsCache singletonsCache) {
        Assertions.assertNotNull(singletonsCache, "Cannot set null singletons cache on application context");
        this.singletonsCache = singletonsCache;
    }
}
