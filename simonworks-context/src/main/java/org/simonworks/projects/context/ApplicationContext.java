/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.context;

import java.util.Optional;

public interface ApplicationContext {

    String getId();

    String getName();

    <T> T getBean(String name);

    default Optional<ApplicationContext> getParent() {
        return Optional.empty();
    }
}
