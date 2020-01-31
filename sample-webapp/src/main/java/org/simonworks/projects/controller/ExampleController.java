/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.controller;

import org.simonworks.projects.context.BeanContext;
import org.simonworks.projects.context.DefaultWebBeanContext;
import org.simonworks.projects.context.annotations.Get;
import org.simonworks.projects.context.annotations.MethodMapping;
import org.simonworks.projects.context.annotations.PathParam;
import org.simonworks.projects.context.annotations.QueryParam;
import org.simonworks.projects.domain.ExampleBean;

public class ExampleController {

    private BeanContext beanContext;

    public ExampleController() {
        beanContext = new DefaultWebBeanContext(null);
    }

    @Get(
            queryParams = {@QueryParam("id"), @QueryParam("amount")},
            pathParams = {@PathParam("branch")}
    )
    @MethodMapping
    public ExampleBean myMethod(String branch, String id, int amount) {
        return new ExampleBean(branch, id, amount);
    }
}
