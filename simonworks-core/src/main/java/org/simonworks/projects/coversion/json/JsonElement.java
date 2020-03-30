/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

public interface JsonElement<T> {

    String DOUBLE_QUOTE = "\"";

    T getRepresentedValue();

}
