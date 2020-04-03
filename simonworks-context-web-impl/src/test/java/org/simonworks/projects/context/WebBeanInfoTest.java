/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.context.annotations.Deserialize;
import org.simonworks.projects.context.annotations.HttpVerb;
import org.simonworks.projects.context.annotations.Serialize;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebBeanInfoTest {

    WebBeanInfo w;
    WebResourceProcessor p;

    @BeforeEach public void setUp() {
        p = new VersionableUriWebResourceProcessor();
    }

    @Test void testExample() {
        w = new WebBeanInfo(ExampleWebResource.class, p);
        assertNotNull(w);
        assertNotNull(w.getWebResource());
        assertNotNull(w.getVersion());
        assertEquals("/v1.1.1/examples", w.getWebResourcePath());
        Map<HttpVerb, List<WebMethod>> m = w.getWebMethodMappings();
        assertNotNull(m);
        assertFalse(m.isEmpty());
        System.out.println(m);

        List<WebMethod> post = m.get(HttpVerb.POST);
        assertNotNull(post);
        assertFalse(post.isEmpty());
        assertEquals(1, post.size());
        WebMethod wm1 = post.get(0);
        assertEquals("/", wm1.getExposedName());
        assertNull(wm1.getVariableNames());

        List<WebMethod> get = m.get(HttpVerb.GET);
        assertNotNull(get);
        assertFalse(get.isEmpty());
        assertEquals(3, get.size());
        System.out.println(get);

        WebMethod method = w.getMethod(HttpVerb.GET, "/names", Arrays.asList("prefix", "suffix"));
        assertNotNull(method);
        assertNotNull(method.getVariableNames());

        WebMethod method2 = w.getMethod(HttpVerb.GET, "/names");
        assertNotNull(method2);
        assertNull(method2.getVariableNames());

        WebMethod method3 = w.getMethod(HttpVerb.GET, "/", null);
        assertNull(method3);

    }

    @Test void testAnnotations() throws NoSuchMethodException {
        testMethod( ExampleWebResource.class.getMethod("insert", ExampleWebResource.ExampleBean.class) );
        /*AnnotatedType[] annotatedParameterTypes = insert.getAnnotatedParameterTypes();
        for(AnnotatedType at : annotatedParameterTypes) {
            if(at.isAnnotationPresent(Deserialize.class)) {
                Type t = at.getType();
                System.out.println(t);
            }
        }*/
        testMethod( ExampleWebResource.class.getMethod("getByNameOrPrefix", String.class, String.class) );


    }

    private void testMethod(Method m) {
        int[] i = {0};
        Arrays
                .stream(m.getAnnotatedParameterTypes())
                .filter(annotatedType -> annotatedType.isAnnotationPresent(Deserialize.class))
                .forEach(annotatedType -> {
                    i[0]++;
                    Type t = annotatedType.getType();
                    System.out.println(t);
                });
        if(i[0] > 1) {
            throw new RuntimeException("Deserialization not supported");
        }



        if(m.getAnnotatedReturnType().isAnnotationPresent(Serialize.class)) {
            Serialize ser = m.getAnnotatedReturnType().getAnnotation(Serialize.class);
            System.out.println(m.getAnnotatedReturnType().getType());
            Type type = m.getAnnotatedReturnType().getType();
            System.out.println(type.getTypeName());

            if (type instanceof GenericArrayType) {
                System.out.println("GenericArrayType");
            } else if (type instanceof ParameterizedType) {
                System.out.println("ParameterizedType");
                ParameterizedType pt = (ParameterizedType) type;
                printType(pt);
            } else if (type instanceof TypeVariable<?>) {
                System.out.println("TypeVariable");
            }
        }
    }

    void printType(Type[] types) {
        for(Type t : types) {
            printType(t);
        }
    }


    void printType(ParameterizedType pt) {
        System.out.println("ParameterizedType.getActualTypeArguments");
        printType(pt.getActualTypeArguments());
        System.out.println("ParameterizedType.OwnerType");
        printType(pt.getOwnerType());
        System.out.println("ParameterizedType.RawType");
        printType(pt.getRawType());
    }

    void printType(Type type) {
        if(type != null && !"java.lang.Object".equals(type.getTypeName())) {
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

}