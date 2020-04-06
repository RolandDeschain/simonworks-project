/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.reflection;

import java.lang.reflect.*;

public class GenericArrayTypeImpl implements GenericArrayType {

    /**
     * Returns a {@code Type} object representing the component type
     * of this array. This method creates the component type of the
     * array.  See the declaration of {@link
     * ParameterizedType ParameterizedType} for the
     * semantics of the creation process for parameterized types and
     * see {@link TypeVariable TypeVariable} for the
     * creation process for type variables.
     *
     * @return a {@code Type} object representing the component type
     * of this array
     * @throws TypeNotPresentException             if the underlying array type's
     *                                             component type refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if  the
     *                                             underlying array type's component type refers to a
     *                                             parameterized type that cannot be instantiated for any reason
     */
    @Override
    public Type getGenericComponentType() {
        return null;
    }

    /**
     * Returns a string describing this type, including information
     * about any type parameters.
     *
     * @return a string describing this type
     * @implSpec The default implementation calls {@code toString}.
     * @since 1.8
     */
    @Override
    public String getTypeName() {
        return null;
    }
}
