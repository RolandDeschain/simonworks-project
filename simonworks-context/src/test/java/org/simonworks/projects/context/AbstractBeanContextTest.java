/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.factory.BeanFactory;
import org.simonworks.projects.reflection.Typed;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class AbstractBeanContextTest {

    static AbstractBeanContext context = mock(AbstractBeanContext.class);
    private static ExampleBean exampleBean = new ExampleBean();

    @BeforeAll
    public static void beforeAll() {
        when(context.get(any(BeanInfo.class))).thenReturn(mock(BeanInfo.class));
        when(context.getBeanFactory()).thenReturn(mock(BeanFactory.class));
        when(context.getBean(anyString())).thenReturn( exampleBean );
        when(context.getBeanRegistry()).thenReturn(mock(BeanRegistry.class));
        when(context.getSingletonsCache()).thenReturn(mock(SingletonsCache.class));
        doNothing().when(context).doHandleBeanAnnotations(anyObject(), any(BeanInfo.class));
        doNothing().when(context).handleBeanAnnotations(anyObject(), any(BeanInfo.class));
        doCallRealMethod().when(context).getId();
        doCallRealMethod().when(context).setId(anyString());
        doCallRealMethod().when(context).getName();
        doCallRealMethod().when(context).setName(anyString());
    }

    @Test
    void testGetBean() {
        assertSame(exampleBean, context.getBean(""));
    }

    @Test
    void get() {
        BeanInfo be = new BeanInfo(new Typed(ExampleBean.class));
        Object o = context.get(be);
        assertNotNull(o);
    }

    @Test
    void getsetId() {
        assertEquals(null, context.getId());
        context.setId("id1");
        assertEquals("id1", context.getId());
    }

    @Test
    void getsetName() {
        assertEquals(null, context.getName());
        context.setName("id1");
        assertEquals("id1", context.getName());
    }

    @Test
    void getBeanFactory() {
        assertNotNull(context.getBeanFactory());
    }

    @Test
    void getBeanRegistry() {
        assertNotNull(context.getBeanRegistry());
    }

    @Test
    void getSingletonsCache() {
        assertNotNull(context.getSingletonsCache());
    }
}