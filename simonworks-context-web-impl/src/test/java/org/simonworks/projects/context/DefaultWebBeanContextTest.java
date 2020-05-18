/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.context.annotations.HttpVerb;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DefaultWebBeanContextTest {

    private static DefaultWebBeanContext context;

    @BeforeAll public static void beforeAll() {
        WebResourceProcessor webResourceProcessor = new VersionableUriWebResourceProcessor();
        BeanRegistryProvider provider = new WebBeanRegistryProvider(webResourceProcessor,"org.simonworks.projects.context");
        context = new DefaultWebBeanContext(provider.loadBeanRegistry());
    }

    @Test public void testAnnotations() {
        BeanInfo info = context.getBeanRegistry().getBeanInfo("examplesWebResource");
        assertNotNull(info);
        info = context.getBeanRegistry().getBeanInfo("/v1.1.1/examples");
        assertEquals(info.getClass(), WebBeanInfo.class);
        WebBeanInfo wInfo = (WebBeanInfo) info;
        assertTrue(wInfo.getBeanClass().equals(ExampleWebResource.class));
        ExampleWebResource example = context.getBean("examplesWebResource");
        assertNotNull(example);
        ExampleWebResource example2 = context.getBean("examplesWebResource");
        assertSame(example, example2);
        ExampleWebResource example3 = context.getBean("/v1.1.1/examples");
        assertSame(example, example3);

        Map<HttpVerb, List<WebMethod>> webMethodMappings = wInfo.getWebMethodMappings();
        System.out.println(webMethodMappings);
    }

}