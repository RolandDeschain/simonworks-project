/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion;

/**
 * Object able to serialize an Object into a String.
 */
public interface Serializer {

    <I> String serialize(I input);

}
