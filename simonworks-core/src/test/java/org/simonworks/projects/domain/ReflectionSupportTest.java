/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.SimpleBean;
import org.simonworks.projects.reflection.ReflectionSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.function.Function;

class ReflectionSupportTest {

    private SimpleBean sb;

    @BeforeEach void setUp() {
        sb = new SimpleBean();
    }

    @Test
    void invokeVoidMethod() throws NoSuchMethodException {
        Object r = ReflectionSupport.invokeMethod(sb, "setName", "Simone");
        System.out.println(r);
        r = ReflectionSupport.invokeMethod(sb, "setName", "Simone");
        System.out.println(r);

        for(Method m : SimpleBean.class.getMethods()) {
            System.out.println(m.getName());
        }

        Method m = SimpleBean.class.getMethod("setBoolField", boolean.class);
        System.out.println(m.getReturnType());
    }

    private interface TestIF<I, O, A> {
        O process(I input);
        O process(I input, A avg);
    }

    private class TestImpl<R> implements TestIF<R, SimpleBean, Function<String, SimpleBean>> {

        @Override
        public SimpleBean process(R input) {
            return null;
        }

        @Override
        public SimpleBean process(R input, Function<String, SimpleBean> avg) {
            return null;
        }
    }

    @Test <G> void testTypes() {
        // SimpleBean sb = new SimpleBean();
        // Class<? extends SimpleBean> aClass = sb.getClass();
        // printType((Type) aClass);
        printType((Type) TestImpl.class);
        G[] genericArray = newArray(TestIF.class);
        printType((Type) genericArray.getClass());
    }

    private <A, B> A[] newArray(Class<B> clazz) {
        return (A[]) Array.newInstance(clazz, 10);
    }

    void printType(Type[] types) {
        for(Type t : types) {
            printType(t);
        }
    }

    void printType(Type type) {
        if(!"java.lang.Object".equals(type.getTypeName())) {
            System.out.println(type.getTypeName());

            if (type instanceof GenericArrayType) {
                printType((GenericArrayType) type);
            } else if (type instanceof ParameterizedType) {
                printType((ParameterizedType) type);
            } else if (type instanceof TypeVariable<?>) {
                printType((TypeVariable<?>) type);
            }

            if (type instanceof Class<?>) {
                printType((Class<?>) type);
            }
        }
    }

    void printType(Class<?> c) {
        if(Object.class != c) {
            System.out.println("Class");
            System.out.println(c);
            printType((AnnotatedElement) c);
            printType((GenericDeclaration) c);
        }
    }

    void printType(GenericArrayType gat) {
        System.out.println("GenericArrayType.getGenericComponentType");
        printType(gat.getGenericComponentType());
    }

    void printType(ParameterizedType pt) {
        System.out.println("ParameterizedType.getActualTypeArguments");
        printType(pt.getActualTypeArguments());
        System.out.println("ParameterizedType.OwnerType");
        printType(pt.getOwnerType());
        System.out.println("ParameterizedType.RawType");
        printType(pt.getRawType());
    }

    void printType(TypeVariable<?>[] typeVariables) {
        if (typeVariables.length != 0) {
            System.out.println("TypeVariables");
            for (TypeVariable<?> tv : typeVariables) {
                printType(tv);
            }
        }
    }

    void printType(TypeVariable<?> tv) {
        System.out.println("TypeVariable " + tv.toString() );
        System.out.println("Typevariable name " + tv.getName());
         System.out.println("TypeVariable.AnnotatedBounds");
         printType( tv.getAnnotatedBounds() );
         System.out.println("TypeVariable.Bounds");
         printType(tv.getBounds());
        // printType(tv.getGenericDeclaration());
        printType((AnnotatedElement) tv);
    }

    void printType(GenericDeclaration gd) {
        System.out.println("GenericDeclaration");
        printType( gd.getTypeParameters() );
    }

    void printType(AnnotatedType[] annotatedTypes) {
        for(AnnotatedType at : annotatedTypes) {
            printType(at);
        }
    }

    void printType(AnnotatedElement ae) {
        System.out.println("AnnotatedElement");
        printType( ae.getAnnotations() );
        printType( ae.getDeclaredAnnotations() );
    }

    void printType(AnnotatedType at) {
        System.out.println("AnnotatedType");
        printType(at.getType());
        printType((AnnotatedElement) at);
    }

    void printType(Annotation[] annotations) {
        if(annotations.length != 0) {
            System.out.println("Annotations");
            for (Annotation a : annotations) {
                printType(a);
            }
        }
    }

    void printType(Annotation a) {
        System.out.println("Annotation");
        printType(a.annotationType());
    }

    @Test
    void invokeMethodWithReturn() {
    }

    @Test
    void writeValue() {
    }

    @Test
    void checkField() {
    }

    @Test
    void readFields() {
    }
}