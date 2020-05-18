/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.Test;
import org.simonworks.projects.context.annotations.HttpVerb;
import org.simonworks.projects.context.annotations.MethodMapping;
import org.simonworks.projects.context.annotations.PathParam;
import org.simonworks.projects.utils.AssertionError;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

class WebMethodBuilderTest {

    @MethodMapping
    public void testSimpleMethodMapping_Method() {}

    @Test void testSimpleMethodMapping() throws NoSuchMethodException {
        WebMethod wm =
                WebMethodBuilder.buildWebMethod(
                        WebMethodBuilderTest.class.getMethod("testSimpleMethodMapping_Method"));
        assertEquals("/", wm.getExposedName());
        assertNull(wm.getPathParams());
    }

    @MethodMapping(
            path = "/foo/bar/zoo"
    )
    public void testCompleMethodMapping_noVariables_Method() {}

    @Test void testCompleMethodMapping_noVariables() throws NoSuchMethodException {
        WebMethod wm = WebMethodBuilder.buildWebMethod(
                WebMethodBuilderTest.class.getMethod("testCompleMethodMapping_noVariables_Method"));
        assertEquals("/foo/bar/zoo", wm.getExposedName());
        assertNull(wm.getPathParams());
    }

    @MethodMapping(
            path = "/foo/bar/zoo/{var1}/{var2}"
    )
    public void testCompleMethodMapping_withVariables_Method(
            @PathParam("var1") Integer var1,
            @PathParam("var2") String name) {}

    @Test void testCompleMethodMapping_withVariables() throws NoSuchMethodException {
        WebMethod wm = WebMethodBuilder.buildWebMethod(
                WebMethodBuilderTest.class.getMethod("testCompleMethodMapping_withVariables_Method", Integer.class, String.class));
        assertEquals("/foo/bar/zoo", wm.getExposedName());
        assertNotNull(wm.getVariableNames());
        assertTrue(wm.getVariableNames().contains("var1"));
        assertTrue(wm.getVariableNames().contains("var2"));
    }

    @MethodMapping(
            path = "/foo/bar/{var1}/zoo"
    )
    public void testInvalidMethodMappingPath_thanError_Method(
            @PathParam("var1") String different) {}

    @Test void testInvalidMethodMappingPath_thanError() {
        assertThrows(AssertionError.class,
                () -> WebMethodBuilder.buildWebMethod(
                        WebMethodBuilderTest.class.getMethod("testInvalidMethodMappingPath_thanError_Method", String.class)));
    }
}