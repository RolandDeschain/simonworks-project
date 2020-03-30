/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context.annotations;

/**
 * This enum defines to which Http Verb methods, marked with {@link MethodMapping}, will be exposed to.
 */
public enum HttpVerb {

    GET,
    POST,
    PUT,
    PATCH,
    DELETE
}
