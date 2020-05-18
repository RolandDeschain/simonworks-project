/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.reflection;

import org.simonworks.projects.utils.Assertions;
import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Type generic information bean that carries on also the original raw class.
 */
public class Typed<T> {

    private Type type;
    private Class<? super T> rawType;

    /**
     * Based on PECS specification producer-extends, consumer-super
     * @param rawType
     *  The raw type associated to this {@link Typed}
     */
    public Typed(Class<? super T> rawType) {
        Assertions.assertNotNull(rawType, "Type class cannot be null");
        this.rawType = rawType;
        this.type = resolve(rawType);
    }

    /**
     * {@link Type} is resolved (normalized) while rawType is extracted
     * @param type
     *  The generic {@link Type} associated to this Typed
     */
    // Suppress warnings since cast should succeed
    @SuppressWarnings({"unchecked"})
    public Typed(Type type) {
        this.type = resolve(type);
        this.rawType = (Class<? super T>) resolveRawType(type);
    }

    private static Type resolve(Type type) {
        Type result = type;
        if(type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            result = clazz.isArray() ? GenericArrayTypeImpl.make(type) : clazz;
        }
        return result;
    }

    private static Class<?> resolveRawType(Type type) {
        Class<?> result = null;
        if(type instanceof Class) {
            result = (Class<?>) type;
        } else if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            result = resolveRawType( parameterizedType.getRawType() );
        } else if(type instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type;
            result = Array.newInstance(
                    resolveRawType(
                            genericArrayType.getGenericComponentType()), 0)
                    .getClass();
        } else if(type instanceof TypeVariable) {
            // TypeVariable bounds are multiple while this method must return a single Class
            // Anyway, as tests showed, any generic bound is Object.class at runtime.
            return Object.class;
        } else if(type instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) type;
            Type[] upperBounds = wildcardType.getUpperBounds();
            result =
                    (upperBounds == null || upperBounds.length == 0)
                    ? Object.class
                            : resolveRawType(upperBounds[0]);
        }
        return result;
     }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
        return rawType.isAnnotationPresent(annotationClass);
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
        return rawType.getAnnotation(annotationClass);
    }

    public Class<? super T> getRawType() {
        return rawType;
    }

    public Type getType() {
        return type;
    }
}
