/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Class.forName;
import static java.util.Collections.synchronizedList;
import static org.simonworks.projects.utils.Assertions.assertNotNull;

public class DefaultBeanFactory implements BeanFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBeanFactory.class);

    private List<BeanCreationPhase> creationPipeline = synchronizedList(new ArrayList<>());

    public DefaultBeanFactory() {
        creationPipeline.addAll(
                Arrays.asList(
                        new PostConstructPhase(),
                        new ConfigurationPhase()));
    }

    public DefaultBeanFactory(List<BeanCreationPhase> creationPipeline) {
        this();
        this.creationPipeline.addAll(creationPipeline);
    }

    @Override
    public <T> T create(String qualifiedName) {
        assertNotNull(qualifiedName, "Class to create cannot be null!");
        try {
            Class<?> clazz = forName(qualifiedName);
            return (T) create(clazz);
        } catch (ClassNotFoundException e) {
            throw new BeanCreationException("Cannot create object " + qualifiedName, e);
        }
    }

    @Override
    public <T> T create(Class<T> clazz) {
        assertNotNull(clazz, "Class to create cannot be null!");
        try {
            T result = clazz.newInstance();
            applyCreationPipeline(result);
            if(LOGGER.isTraceEnabled()) {
                LOGGER.trace("Just created object <{}> of class <{}>", result, clazz);
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BeanCreationException("Object of class " + clazz + " is missing default public constructor", e);
        }
    }

    private <T> void applyCreationPipeline(T result) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("Applying creation pipeline <{}> on object <{}>", creationPipeline, result);
        }
        creationPipeline.forEach(
                beanCreationPhase -> beanCreationPhase.apply(result));
    }
}
