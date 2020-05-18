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
 * This annotation is meant to be used on the inputs of the methods of a web resource
 * in order to indicate the type of conversion to be applied before invoking them
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Deserialize {

    /**
     * The name of a {@link org.simonworks.projects.conversion.Deserializer} bean in the context.
     * Default available names are : 'jsonDeserializer', "xmlDeserializer'
     * @return
     *  The {@link org.simonworks.projects.conversion.Deserializer}'s name
     */
    String deserializerName();
}
