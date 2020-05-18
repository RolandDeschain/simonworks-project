/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.controller;

import org.simonworks.projects.context.BeanContext;
import org.simonworks.projects.context.annotations.*;
import org.simonworks.projects.domain.ExampleBean;

import java.util.UUID;

@WebResource(
        name = "examplesResource",
        version = @Version(major = 0, minor = 1, patch = 0, description = "first version"))
public class ExampleController {

    private BeanContext beanContext;

    @MethodMapping(path = "/examples/{branch}")
    public ExampleBean myMethod(
            @PathParam("branch") String branch,
            @QueryParam("id") String id,
            @QueryParam("amount") int amount) {
        return new ExampleBean(branch, id, amount);
    }

    @MethodMapping(path = "/examples")
    public ExampleBean allExamples() {
        return new ExampleBean("firstExample", UUID.randomUUID().toString(), 1);
    }

    public BeanContext getBeanContext() {
        return beanContext;
    }
}
