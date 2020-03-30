/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

/**
 * {@link State} extension introducing support for storable memory to be used for
 * objects created by the State
 */
public interface StorableMemoryState<T> extends State {

    /**
     * Views last object inserted into memory without extracting it.
     *
     * @return
     *  last object inserted into memory.
     */
    T view();

    /**
     * Extracts last object inserted into memory.
     *
     * @return
     *  last object inserted into memory.
     */
    T extract();

    /**
     * Stores an object into memory.
     *
     * @param object
     *  The object to store.
     */
    void store(T object);

}
