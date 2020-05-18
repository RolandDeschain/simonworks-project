/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.simonworks.projects.annotations.PostConstructor;

import java.lang.reflect.InvocationTargetException;

import static java.util.Arrays.stream;

final class PostConstructPhase implements BeanCreationPhase {

    @Override
    public void apply(Object obj) {
        Class<?> clazz = obj.getClass();
        stream(clazz.getMethods())
                .filter(method -> method.isAnnotationPresent(PostConstructor.class))
                .forEach(method -> {
                    try {
                        method.invoke(obj);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new BeanCreationException("Cannot apply PostConstructPhase (@PostConstructor annotation) phase on target Object " + obj, e);
                    }
                });
    }
}
