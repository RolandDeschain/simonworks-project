/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation can be used to mark methods eligible to be exposed as web api.
 * Only methods declared by classes annotated with {@link WebResource} annotation will be taken in consideration.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodMapping {

    /**
     * The Http verb to which the method marked with {@link MethodMapping} will be exposed
     * @return
     *  The Http verb to which the method marked with {@link MethodMapping} will be exposed
     */
    HttpVerb verb() default HttpVerb.GET;

    /**
     * The path to which the method marked with {@link MethodMapping} will be exposed
     * @return
     *  The path to which the method marked with {@link MethodMapping} will be exposed
     */
    String path() default "/";




}
