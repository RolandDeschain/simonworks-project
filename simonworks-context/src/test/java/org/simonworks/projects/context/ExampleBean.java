/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.context.annotation.Dependency;

@Singleton(name = "example")
public class ExampleBean {

    @Dependency(beanName = "samples") private Samples samples;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
