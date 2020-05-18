/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import java.io.IOException;

/**
 * A source of something.
 *
 * @param <S>
 *     The generic type this Source is able to provide.
 */
public interface Source<S> {

    /**
     * The source object this source is able to produce.
     * @return
     *  The source object.
     */
    S getSource() throws IOException;
}
