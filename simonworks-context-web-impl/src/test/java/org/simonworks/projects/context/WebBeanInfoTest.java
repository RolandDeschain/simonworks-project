/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.context.annotations.HttpVerb;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebBeanInfoTest {

    WebBeanInfo w;
    WebResourceProcessor p;

    @BeforeEach public void setUp() {
        p = new VersionableUriWebResourceProcessor();
    }

    @Test void testExample() {
        w = new WebBeanInfo(ExampleWebResource.class, p);
        assertNotNull(w);
        assertNotNull(w.getWebResource());
        assertNotNull(w.getVersion());
        assertEquals("/v1.1.1/examples", w.getWebResourcePath());
        Map<HttpVerb, List<WebMethod>> m = w.getWebMethodMappings();
        assertNotNull(m);
        assertFalse(m.isEmpty());
        System.out.println(m);

        List<WebMethod> post = m.get(HttpVerb.POST);
        assertNotNull(post);
        assertFalse(post.isEmpty());
        assertEquals(1, post.size());
        WebMethod wm1 = post.get(0);
        assertEquals("/", wm1.getExposedName());
        assertNull(wm1.getVariableNames());

        List<WebMethod> get = m.get(HttpVerb.GET);
        assertNotNull(get);
        assertFalse(get.isEmpty());
        assertEquals(3, get.size());
        System.out.println(get);

        WebMethod method = w.getMethod(HttpVerb.GET, "/names", Arrays.asList("prefix", "suffix"));
        assertNotNull(method);
        assertNotNull(method.getVariableNames());

        WebMethod method2 = w.getMethod(HttpVerb.GET, "/names");
        assertNotNull(method2);
        assertNull(method2.getVariableNames());

        WebMethod method3 = w.getMethod(HttpVerb.GET, "/", null);
        assertNull(method3);

    }

}