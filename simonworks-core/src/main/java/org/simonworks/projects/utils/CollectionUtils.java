/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.utils;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {}

    public static boolean isNotEmpty(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean bothNull(Collection<?> c1, Collection<?> c2) {
        return c1 == null && c2 == null;
    }

    public static boolean sameSize(Collection<?> c1, Collection<?> c2) {
        if(c1 == null || c2 == null) {
            return false;
        }
        return c1.size() == c2.size();
    }
}
