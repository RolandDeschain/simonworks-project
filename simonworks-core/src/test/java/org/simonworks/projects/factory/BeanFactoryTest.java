/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.factory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.configurations.ConfigurationException;

class BeanFactoryTest {

    private BeanFactory factory;
    private boolean configInvoked;
    private boolean postConstructInvoked;

    @BeforeEach
    public void setUp() {
        factory = new DefaultBeanFactory();
    }

    @Test
    void createByName() {
        Example e = factory.create("org.simonworks.projects.factory.Example");
        asserts(e);
    }

    @Test
    void createByClass() {
        Example e = factory.create(Example.class);
        asserts(e);
    }

    private void asserts(Example e) {
        Assertions.assertNotNull(e);
        Assertions.assertEquals(Example.class, e.getClass());
        Assertions.assertTrue(e.configInvoked);
        Assertions.assertTrue(e.postConstructInvoked);
        Assertions.assertEquals(1, e.a);
    }

    @Test
    void createByNotExistingName_thenException() {
        Assertions.assertThrows(BeanCreationException.class,
                () -> factory.create("NotExists"));
    }

    @Test
    void createByNotExistingClass_thenException() {
        Assertions.assertThrows(BeanCreationException.class,
                () -> factory.create(NoDefaultConstructorClass.class));
    }

    class NoDefaultConstructorClass {
        public NoDefaultConstructorClass(String s) {
        }
    }

}