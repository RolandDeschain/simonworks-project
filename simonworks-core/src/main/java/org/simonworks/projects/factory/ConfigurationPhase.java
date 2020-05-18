/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.simonworks.projects.annotations.Configurable;
import org.simonworks.projects.configurations.Configuration;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

final class ConfigurationPhase implements BeanCreationPhase {

    @Override
    public void apply(Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Configurable.class)) {
            Configurable configurable = clazz.getAnnotation(Configurable.class);
            Class<? extends Configuration> configurationClass = configurable.configurationClass();
            String configurationMethodName = configurable.configurationMethod();
            String configurationFile = configurable.configurationFile();
            try {
                Constructor<? extends Configuration> configurationClassConstructor = configurationClass.getConstructor(String.class);
                Configuration configuration = configurationClassConstructor.newInstance(configurationFile);
                Method configurationMethod = clazz.getDeclaredMethod(configurationMethodName, Configuration.class);
                boolean accessibility = configurationMethod.isAccessible();
                configurationMethod.setAccessible(true);
                configurationMethod.invoke(obj, configuration);
                configurationMethod.setAccessible(accessibility);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new BeanCreationException("Cannot apply ConfigurablePhase (@Configurable annotation) phase on target Object " + obj, e);
            }
        }
    }
}
