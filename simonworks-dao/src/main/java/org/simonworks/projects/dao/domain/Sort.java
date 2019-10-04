/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.domain;

import java.util.Optional;

import static java.util.Optional.empty;

public class Sort {

    private static final Sort DEFAULT_SORT = new Sort(null);

    public enum Direction {
        ASC,
        DESC;

        public Direction reverse() {
            if (this == ASC) {
                return DESC;
            } else return ASC;
        }
    }

    private Direction direction;

    private Sort(Direction direction) {
        this.direction = Optional.of(direction).orElse(Direction.DESC);
    }

    public static Sort get() {
        return DEFAULT_SORT;
    }

    public static Sort get(Direction direction) {
        return new Sort(direction);
    }

    public Direction getDirection() {
        return direction;
    }

    public Sort reverse() {
        return new Sort(this.direction.reverse());
    }
}
