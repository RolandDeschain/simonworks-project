/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.proxies;

import java.lang.reflect.InvocationHandler;

public abstract class AbstractInvocationHandler<T> implements InvocationHandler {

    private T realObject;

    public AbstractInvocationHandler(T realObject) {
        this.realObject = realObject;
    }

    protected T getRealObject() {
        return realObject;
    }
}
