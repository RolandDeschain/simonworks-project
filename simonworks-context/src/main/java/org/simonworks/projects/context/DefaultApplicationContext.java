/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotation.Dependency;
import org.simonworks.projects.factory.BeanFactory;
import org.simonworks.projects.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class DefaultApplicationContext extends AbstractApplicationContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultApplicationContext.class);

    public DefaultApplicationContext(BeanRegistryProvider beanRegistryProvider) {
        super(beanRegistryProvider);
    }

    public DefaultApplicationContext(BeanFactory beanFactory,
                                     BeanRegistryProvider beanRegistryProvider,
                                     SingletonsCache singletonsCache) {
        super(
                beanFactory,
                beanRegistryProvider,
                singletonsCache);
    }


    @Override
    public <T> T getBean(String name) {
        Object result;
        // First recover bean info
        BeanInfo beanInfo = getBeanRegistry().getBeanInfo(name);
        switch(beanInfo.getLifecycle()) {
            default: case PROTOTYPE: {
                /*
                 * Simply create a new instance
                 */
                result = getBeanFactory().create(beanInfo.getBeanClass());
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Created prototype bean <{}> from using info <{}>", result, beanInfo);
                }
                break;
            }
            case SINGLETON: {
                /*
                 * Checking singletons cache for a ready to use instance
                 */
                result = getSingletonsCache().get(name);
                if( result == null ) {
                    /*
                     * Create the only singletons instance
                     */
                    result = getBeanFactory().create(beanInfo.getBeanClass());
                    if(LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Created singleton bean <{}> using bean info <{}>. Registering in singleton cache with the name <{}>",
                                result, beanInfo, name);
                    }
                    /*
                     * Register singleton
                     */
                    getSingletonsCache().put(name, result);
                } else {
                    if(LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Recovered singleton bean <{}> from name <{}>", result, name);
                    }
                }
            }
        }
        fillDependencies(result, beanInfo.getDependencies());
        checkRequiredApplicationContext(result, beanInfo.getInjectableApplicationContext());
        completeBean(result, beanInfo.getCompleteSetup());
        return (T) result;
    }

    private void completeBean(Object result, Method completeSetup) {
        if(completeSetup != null) {
            boolean accessible = completeSetup.isAccessible();
            completeSetup.setAccessible(true);
            try {
                completeSetup.invoke(result);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            completeSetup.setAccessible(accessible);
        }
    }

    private void checkRequiredApplicationContext(Object result, Field injectableApplicationContext) {
        if(injectableApplicationContext != null) {
            boolean accessible = injectableApplicationContext.isAccessible();
                injectableApplicationContext.setAccessible(true);
            try {
                injectableApplicationContext.set(result, this);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            injectableApplicationContext.setAccessible(accessible);
        }
    }

    private void fillDependencies(Object result, Map<Field, Dependency> dependencies) {
        dependencies.forEach((field, d) -> {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            Object bean = getBean(d.beanName());
            try {
                field.set(result, bean);
                String afterInjectionMethod = d.afterInjectionMethod();
                if(StringUtils.isNotEmpty(afterInjectionMethod) ) {
                    Method method = result.getClass().getMethod(afterInjectionMethod);
                    method.invoke(result);
                }
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            field.setAccessible(accessible);
        });
    }
}
