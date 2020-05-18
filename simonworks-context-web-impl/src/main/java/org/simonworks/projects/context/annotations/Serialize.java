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
 * This annotation is meant to be used on the return type of the methods of a {@link WebResource}
 * in order to indicate the type of conversion to be applied before returning
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Serialize {

    /**
     * The name of a {@link org.simonworks.projects.conversion.Serializer} bean in the context.
     * Default available names are : 'jsonSerializer', "xmlSerializer'
     * @return
     *  The {@link org.simonworks.projects.conversion.Serializer}'s name
     */
    String serializerName();
}
