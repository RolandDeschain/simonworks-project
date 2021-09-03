/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import java.util.Set;
import java.util.stream.Stream;

/**
 * A {@link ListableBeans} instance is able to provide information related to Beans.
 */
public interface ListableBeans {

    Set<String> beanNamesSet();

    default Stream<String> beanNamesStream() {
       return beanNamesSet().stream();
    }

    default boolean containsBean(String beanName) {
        return beanNamesSet().contains(beanName);
    }
}
