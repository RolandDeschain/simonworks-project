/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.reflection;

import org.simonworks.projects.utils.Assertions;

import java.lang.reflect.AccessibleObject;

/**
 * Allows accessibility of an {@link java.lang.reflect.AccessibleObject}.
 * Typical usage is inside a try-with-resource statement: this will guarantee that previous accessibility of underlying
 * {@link AccessibleObject} will be restored at the end of the statement.
 */
public class AccessibleObjectAllower implements AutoCloseable {

    private AccessibleObject accessibleObject;
    private boolean previousAccessibility;

    public AccessibleObjectAllower(AccessibleObject accessibleObject) {
        Assertions.assertNotNull(accessibleObject, "Cannot allow accessibility on null!");
        this.accessibleObject = accessibleObject;
        this.previousAccessibility = accessibleObject.isAccessible();
        if(!this.previousAccessibility) {
            this.accessibleObject.setAccessible(true);
        }
    }

    /**
     * Restores previous accessibility of the underlying {@link AccessibleObject}.
     */
    @Override
    public void close() {
        if(!this.previousAccessibility) {
            accessibleObject.setAccessible(false);
        }
    }
}
