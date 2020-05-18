/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.MethodMapping;
import org.simonworks.projects.utils.Assertions;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator to be used to extact path items from {@link MethodMapping#path()}. Path items can have following formats:</br>
 * <ul>
 *     <li>/</li>
 *     <li>/foo</li>
 *     <li>/foo/</li>
 *     <li>/foo/bar</li>
 *     <li>/foo/bar/</li>
 *     <li>/foo/{var1}</li>
 *     <li>/foo/{var1}/</li>
 *     <li>/foo/bar/{var1}</li>
 *     <li>/foo/bar/{var1}/{var2}</li>
 *     <li>/foo/bar/{var1}/{var2}/</li>
 *     <li>/{var1}</li>
 *     <li>/{var1}/</li>
 *     <li>ecc ...</li>
 * </ul>
 */
public class WebPathIterator implements Iterator<String> {

    private int i = 0;
    private final String path;

    public WebPathIterator(String path) {
        Assertions.assertNotNull(path, "Path to walk on cannot be null");
        Assertions.assertTrue(path.startsWith("/"), "Path must start with \"/\" character: " + path);
        this.path = path;
    }

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return i != -1;
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public String next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        int j = path.indexOf('/', i + 1);
        String r = (j == -1) ? path.substring(i)
                : path.substring(i, j);
        i = j;
        return r;
    }
}
