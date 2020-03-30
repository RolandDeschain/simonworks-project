/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class StringUtils {

    private StringUtils() {}

    public static final String EMPTY = "";

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static String requireNotEmpty(String s) {
        return requireNotEmpty(s, "Not empty String is required but <" + s + "> was given");
    }

    public static String requireNotEmpty(String s, String message) {
        if(isEmpty(s)) {
            throw new NullPointerException(message);
        }
        return s;
    }

    public static Set<Character> charsetOf(String source) {
        Set<Character> s = new HashSet<>();
        for(char c : source.toCharArray()) {
            s.add(c);
        }
        return Collections.unmodifiableSet(s);
    }

}
