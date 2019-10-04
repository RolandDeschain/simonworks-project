/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.utils;

public interface StringUtils {

    String EMPTY = "";

    static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

}
