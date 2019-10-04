/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.annotations.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.StringJoiner;

public interface BeanRegistry {

    Logger LOGGER = LoggerFactory.getLogger(BeanRegistry.class);

    void registerBean(BeanInfo info);

    void unregisterBean(String name);

    BeanInfo getBeanInfo(String name);

    default void registerBean(Class<?> clazz) {
        registerBean(clazz, readLifecycle(clazz));
    }

    default void registerBean(Class<?> clazz, BeanInfo.Lifecycle lifecycle) {
        if(isScopedClass(clazz)) {
            String name = getAnnotatedBeanName(clazz);
            if (name == null || name.length() == 0) {
                name = determineBeanName(clazz);
            }
            BeanInfo info = new BeanInfo(
                    name,
                    clazz,
                    lifecycle);
            registerBean(info);
        }
    }

    default BeanInfo.Lifecycle readLifecycle(Class<?> clazz) {
        BeanInfo.Lifecycle result = null;
        if (clazz.isAnnotationPresent(Singleton.class)) {
            result = BeanInfo.Lifecycle.SINGLETON;
        } else if (clazz.isAnnotationPresent(Prototype.class)){
            result = BeanInfo.Lifecycle.PROTOTYPE;
        }
        return result;
    }

    default String getAnnotatedBeanName(Class<?> clazz) {
        String result;
        Prototype prototype = clazz.getAnnotation(Prototype.class);
        if(prototype != null) {
            result = prototype.name();
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Prototype name for class <{}> is <{}>", clazz, result);
            }
        } else {
            Singleton singleton = clazz.getAnnotation(Singleton.class);
            result = singleton.name();
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Singleton name for class <{}> is <{}>", clazz, result);
            }
        }
        return result;
    }

    default boolean isScopedClass(Class<?> clazz) {
        return clazz.isAnnotationPresent(Prototype.class) || clazz.isAnnotationPresent(Singleton.class);
    }

    default String determineBeanName(Class<?> clazz) {
        String name = clazz.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        char[] chars = name.toCharArray();
        StringJoiner joiner = new StringJoiner("");
        for(char c : chars) {
            if(Character.isUpperCase(c)) {
                joiner.add("-" + Character.toLowerCase(c));
            } else {
                joiner.add(String.valueOf(c));
            }
        }
        String result = joiner.toString().substring(1);
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Auto-determined bean name for class <{}> is <{}>", clazz, result);
        }
        return result;
    }

}
