/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import java.util.Objects;

/**
 * This class represents a couple of objects
 * @param <A> First object's generic type
 * @param <B>Second object's generic type
 */
public class Couple<A, B> {

    private A first;
    private B second;

    private Couple(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public static final <A, B> Couple of(A first, B second) {
        return new Couple(first, second);
    }

    public static final <C> Couple pair(C first, C second) {
        return new Couple(first, second);
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Couple<?, ?> couple = (Couple<?, ?>) o;
        return first.equals(couple.first) &&
                second.equals(couple.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
