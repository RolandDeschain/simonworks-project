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
 * Annotation used to flag a bean as a web resource.
 * Beans annotated with {@link WebResource} annotation are eligible to expose method as web api.
 * Exposed resource will take the name provided by the {@link WebResource#name()} attribute and the version provided by the {@link WebResource#version()} attribute.
 * The web path to which this web resource will be available depends on the {@link org.simonworks.projects.context.WebResourceProcessor} implementation
 * present under the name "webResource" in current {@link org.simonworks.projects.context.WebBeanContext}
 *
 * @see org.simonworks.projects.context.WebResourceProcessor
 * @see org.simonworks.projects.context.WebBeanContext
 * @see Version
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebResource {

    /**
     * This {@link WebResource}'s name
     * @return
     *  The {@link WebResource}'s name
     */
    String name();

    /**
     * This {@link WebResource}'s description
     * @return
     *  The {@link WebResource}'s description
     */
    String description() default "";

    /**
     * The version of this web resource. The version value will be part of the final exposed path before the {@link WebResource#name()} value.
     * @return
     *  The version of this web resource. The version value will be part of the final exposed path before the {@link WebResource#name()} value
     */
    Version version();
}
