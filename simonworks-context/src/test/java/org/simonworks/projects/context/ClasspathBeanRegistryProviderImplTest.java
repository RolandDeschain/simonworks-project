/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClasspathBeanRegistryProviderImplTest {

    @Test
    void loadBeanRegistry() {
        ClasspathBeanRegistryProviderImpl provider = new ClasspathBeanRegistryProviderImpl();
        provider.addPackageToScan("org.simonworks.projects.context");
        ApplicationContext applicationContext = new DefaultApplicationContext(provider);

        ExampleSingleton singleton = applicationContext.getBean("example-singleton");
        assertNotNull(singleton);
        assertSame(applicationContext, singleton.getApplicationContext());
        ExampleSingleton singleton2 = applicationContext.getBean("example-singleton");
        assertNotNull(singleton2);
        assertSame(singleton, singleton2);

        ExamplePrototype prototype = applicationContext.getBean("myTestPrototype");
        assertNotNull(prototype);
        assertSame(singleton, prototype.getTestServiceSingleton());
        ExamplePrototype prototype2 = applicationContext.getBean("myTestPrototype");
        assertNotNull(prototype2);
        assertSame(singleton2, prototype.getTestServiceSingleton());
        assertNotSame(prototype, prototype2);

        prototype.doSomething();
    }
}