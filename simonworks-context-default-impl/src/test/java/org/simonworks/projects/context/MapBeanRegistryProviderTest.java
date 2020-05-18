/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MapBeanRegistryProviderTest {

    @Test
    void loadBeanRegistry() {
        BeanRegistryProvider provider = new MapBeanRegistryProvider("org.simonworks.projects.context");
        BeanContext beanContext = new DefaultBeanContext(provider.loadBeanRegistry());

        ExampleSingleton singleton = beanContext.getBean("example-singleton");
        Assertions.assertNotNull(singleton);
        Assertions.assertSame(beanContext, singleton.getBeanContext());
        ExampleSingleton singleton2 = beanContext.getBean("example-singleton");
        Assertions.assertNotNull(singleton2);
        Assertions.assertSame(singleton, singleton2);

        ExamplePrototype prototype = beanContext.getBean("myTestPrototype");
        Assertions.assertNotNull(prototype);
        Assertions.assertSame(singleton, prototype.getTestServiceSingleton());
        ExamplePrototype prototype2 = beanContext.getBean("myTestPrototype");
        Assertions.assertNotNull(prototype2);
        Assertions.assertSame(singleton2, prototype.getTestServiceSingleton());
        Assertions.assertNotSame(prototype, prototype2);

        prototype.doSomething();
    }
}