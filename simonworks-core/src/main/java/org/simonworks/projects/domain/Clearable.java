/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

        import java.util.Iterator;

/**
 * Convenient interface to clear {@link Iterable} objects
 * @see java.lang.Iterable
 */
public interface Clearable<T> extends Iterable<T> {

    default void clear() {
        Iterator<T> i = iterator();
        while(i.hasNext()) {
            i.next();
            i.remove();
        }
    }
}
