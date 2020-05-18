/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.simonworks.projects.reflection.Typed;

public interface BeanFactory {

    <T> T create(String qualifiedName);

    <T> T create(Class<T> clazz);

    <T> T create(Typed<T> clazz);
}
