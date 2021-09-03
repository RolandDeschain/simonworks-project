/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotation.Dependency;
import org.simonworks.projects.reflection.ReflectionSupport;
import org.simonworks.projects.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;

public class DefaultBeanContext extends AbstractBeanContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBeanContext.class);

    public DefaultBeanContext(BeanRegistry beanRegistry) {
        super(beanRegistry);
    }

    @Override
    public BeanContext self() {
        return this;
    }

    @Override
    protected void doHandleBeanAnnotations(Object bean, BeanInfo beanInfo) {
        fillDependencies(bean, beanInfo.getDependencies());
        checkRequiredBeanContext(bean, beanInfo.getInjectableBeanContext());
        if(beanInfo.getCompleteSetup() != null) {
            ReflectionSupport.invokeMethod(bean, beanInfo.getCompleteSetup());
        }
    }

    private void fillDependencies(Object result, Map<Field, Dependency> dependencies) {
        for (Map.Entry<Field, Dependency> entry : dependencies.entrySet()) {
            Field field = entry.getKey();
            Dependency d = entry.getValue();
            try {
                ReflectionSupport.writeValue(result, field.getName(), getBean(d.beanName()));
                String afterInjectionMethod = d.afterInjectionMethod();
                if (StringUtils.isNotEmpty(afterInjectionMethod)) {
                    ReflectionSupport.invokeMethod(result, afterInjectionMethod);
                }
            } catch (Exception e) {
                LOGGER.error("Exception invoking fillDependencies(Object <{}>, Map<Field, Dependency> <{}>", result, dependencies, e);
            }
        }
    }

}
