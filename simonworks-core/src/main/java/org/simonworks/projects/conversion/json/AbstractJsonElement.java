/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion.json;

import org.simonworks.projects.utils.Assertions;

public abstract class AbstractJsonElement<T> implements JsonElement<T> {

    private T representedValue;

    public AbstractJsonElement(T representedValue) {
        Assertions.assertNotNull(representedValue, "JsonValue's value cannot be null. Consider using JsonNullValue instead");
        this.representedValue = representedValue;
    }

    @Override
    public T getRepresentedValue() {
        return representedValue;
    }

    @Override
    public String toString() {
        return String.valueOf(representedValue);
    }
}
