/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.proxies;

import org.simonworks.projects.annotations.Singleton;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * This factory provides Proxy instances of objects. Exposed methods allow to obtain proxies with desired behaviour.
 */
@Singleton(name = "proxyFactory")
public class ProxyFactory {

    private ProxyFactory() {}

    public static <T> T executionTimerProxy(T realObject) {
        return proxyOf(realObject, new ExecutionTimerInvocationHandler<T>( realObject ));
    }

    private static <T> T proxyOf(T realObject, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(
                realObject.getClass().getClassLoader(),
                realObject.getClass().getInterfaces(),
                handler
        );
    }

}
