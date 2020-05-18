/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.simonworks.projects.conversion.SimpleBean;
import org.simonworks.projects.conversion.UnknownType;
import org.simonworks.projects.conversion.json.JsonParser;
import org.simonworks.projects.reflection.ReflectionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class ReflectionSupportTest {
    
    private static Logger LOGGER = LoggerFactory.getLogger(ReflectionSupportTest.class);

    private SimpleBean sb;

    @BeforeEach void setUp() {
        sb = new SimpleBean();
    }

    @Test <A, B extends ReflectionSupportTest, C> void testClassReflection() {
//        List<TestIF<SimpleBean[], B, Test>> sb =
//                new ArrayList<TestIF<SimpleBean[], B, Test>>();
//        testClass(sb.getClass());

//        LOGGER.debug("###############################");
//        tabs = 1;
//
//        TestIF<SimpleBean[], B, Test> testIf = new TestIF<SimpleBean[], B, Test>() {
//            @Override
//            public B process(SimpleBean[] input) {
//                return null;
//            }
//
//            @Override
//            public B process(SimpleBean[] input, Test avg) {
//                return null;
//            }
//        };
//
//        testClass(testIf.getClass());
//
//        LOGGER.debug("###############################");
//        tabs = 1;

        TestIF<JsonParser<UnknownType>, SimpleBean, Function<String, SimpleBean>> test = new TestImpl<>();
        testClass(test.getClass());
    }



    private static int tabs = 0;
    private static Type lastType;

    private static String tab() {
        char[] c = new char[ tabs ];
        Arrays.fill(c, ' ');
        return new String(c);
    }

    private static String spaces() {
        char[] c = new char[ tabs+4 ];
        Arrays.fill(c, ' ');
        return new String(c);
    }

    public static void print(String s) {
        LOGGER.debug(s);
    }

    private void tabOff() {
        LOGGER.debug("tabOff");
        tabs--;
    }

    private void tabOn() {
        tabs++;
    }

    private void testClass(Class<?> aClass) {
        print("Testing class " + aClass);
        if(aClass.isArray()) {
            print("isArray");
        }
        Class<?> componentType = aClass.getComponentType();
        if(componentType != null) {
            print("Component type " + componentType);
            testClass(componentType);
        }
        Type[] genericInterfaces = aClass.getGenericInterfaces();
        for(Type genericInterface : genericInterfaces) {
            tabOn();
            print("Generic interface " +   genericInterface + " of class " + aClass);
            testType(genericInterface);
            tabOff();
        }

        TypeVariable<? extends Class<?>>[] typeParameters = aClass.getTypeParameters();
        if(typeParameters != null && typeParameters.length > 0) {
            tabOn();
            print("Type parameters of class " + aClass + " = " + typeParameters.length +
                    ", " + Arrays.toString(typeParameters));
            for(TypeVariable<? extends Class<?>> tv : typeParameters) {
                testVariable(tv);
            }
            tabOff();
        }
    }

    private void testVariable(TypeVariable<?> typeVariable) {
        print("tv Class " + typeVariable.getClass() + " " + typeVariable.getName());
        if (typeVariable.getBounds().length == 0) {
            print("No bounds");
        } else {
            testTypes(typeVariable.getBounds());
        }
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        System.out.println("gen declaration " + genericDeclaration);
    }

    private void testType(Type type) {
        tabOn();
        if(type != null ) {
            print("Type " + type + " of " + type.getClass());
            if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                print("pt Class : " + pt.getClass());
                testType(pt.getRawType());
                testType(pt.getOwnerType());
                testTypes(pt.getActualTypeArguments());
            } else if (type instanceof Class) {
                print("type is class");
                testClass((Class<?>) type);
            } else if (type instanceof TypeVariable) {
                TypeVariable<?> typeVariable = (TypeVariable<?>) type;
                testVariable(typeVariable);
            } else {
                print(" unknown type " + type);
            }
        } else {
            print("null");
        }
        tabOff();
    }

    private void testTypes(Type ... types) {
        if(types != null) {
            for(Type t : types) {

                if(t instanceof Class<?> && (Class<?>)t != Object.class){
                    testType(t);
                }
            }
        }
    }

    @Test
    void invokeVoidMethod() throws NoSuchMethodException {
        Object r = ReflectionSupport.invokeMethod(sb, "setName", "Simone");
        LOGGER.debug(String.valueOf(r));
        r = ReflectionSupport.invokeMethod(sb, "setName", "Simone");
        LOGGER.debug(String.valueOf(r));

        for(Method m : SimpleBean.class.getMethods()) {
            LOGGER.debug(m.getName());
        }

        Method m = SimpleBean.class.getMethod("setBoolField", boolean.class);
        LOGGER.debug(m.getReturnType().toString());
    }

    private interface TestIF<I, O, A> {
        O process(I input);
        O process(I input, A avg);
    }

    private class TestImpl<R extends JsonParser> implements TestIF<R, SimpleBean, Function<String, SimpleBean>> {

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
            LOGGER.debug(type.getTypeName());

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
            LOGGER.debug("Class");
            LOGGER.debug(c.toString());
            printType((AnnotatedElement) c);
            printType((GenericDeclaration) c);
        }
    }

    void printType(GenericArrayType gat) {
        LOGGER.debug("GenericArrayType.getGenericComponentType");
        printType(gat.getGenericComponentType());
    }

    void printType(ParameterizedType pt) {
        LOGGER.debug("ParameterizedType.getActualTypeArguments");
        printType(pt.getActualTypeArguments());
        LOGGER.debug("ParameterizedType.OwnerType");
        printType(pt.getOwnerType());
        LOGGER.debug("ParameterizedType.RawType");
        printType(pt.getRawType());
    }

    void printType(TypeVariable<?>[] typeVariables) {
        if (typeVariables.length != 0) {
            LOGGER.debug("TypeVariables");
            for (TypeVariable<?> tv : typeVariables) {
                printType(tv);
            }
        }
    }

    void printType(TypeVariable<?> tv) {
        LOGGER.debug("TypeVariable " + tv.toString() );
        LOGGER.debug("Typevariable name " + tv.getName());
         LOGGER.debug("TypeVariable.AnnotatedBounds");
         printType( tv.getAnnotatedBounds() );
         LOGGER.debug("TypeVariable.Bounds");
         printType(tv.getBounds());
        // printType(tv.getGenericDeclaration());
        printType((AnnotatedElement) tv);
    }

    void printType(GenericDeclaration gd) {
        LOGGER.debug("GenericDeclaration");
        printType( gd.getTypeParameters() );
    }

    void printType(AnnotatedType[] annotatedTypes) {
        for(AnnotatedType at : annotatedTypes) {
            printType(at);
        }
    }

    void printType(AnnotatedElement ae) {
        LOGGER.debug("AnnotatedElement");
        printType( ae.getAnnotations() );
        printType( ae.getDeclaredAnnotations() );
    }

    void printType(AnnotatedType at) {
        LOGGER.debug("AnnotatedType");
        printType(at.getType());
        printType((AnnotatedElement) at);
    }

    void printType(Annotation[] annotations) {
        if(annotations.length != 0) {
            LOGGER.debug("Annotations");
            for (Annotation a : annotations) {
                printType(a);
            }
        }
    }

    void printType(Annotation a) {
        LOGGER.debug("Annotation");
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