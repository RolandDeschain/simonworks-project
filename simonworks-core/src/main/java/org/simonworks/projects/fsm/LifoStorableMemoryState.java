/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.fsm;

import org.simonworks.projects.utils.Assertions;

import java.util.Deque;
import java.util.LinkedList;

/**
 * LIFO (Last-in, Fisrt-out) adapter of {@link StorableMemoryState}
 */
public abstract class LifoStorableMemoryState<T> implements StorableMemoryState<T> {

    private Deque<T> queue;

    public LifoStorableMemoryState() {
        this(new LinkedList<>());
    }

    public LifoStorableMemoryState(Deque<T> queue) {
        Assertions.assertNotNull(queue, "Queue cannot be null!");
        this.queue = queue;
    }

    /**
     * Views last object inserted into memory without extracting it.
     *
     * @return last object inserted into memory.
     */
    @Override
    public T view() {
        return queue.peekFirst();
    }

    /**
     * Extracts last object inserted into memory.
     *
     * @return last object inserted into memory.
     */
    @Override
    public T extract() {
        return queue.removeFirst();
    }

    /**
     * Stores an object into memory.
     *
     * @param object The object to store.
     */
    @Override
    public void store(T object) {
        queue.addFirst(object);
    }
}
