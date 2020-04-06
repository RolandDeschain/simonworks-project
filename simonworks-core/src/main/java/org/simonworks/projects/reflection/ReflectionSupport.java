/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.reflection;

import org.simonworks.projects.domain.MethodInvocationException;
import org.simonworks.projects.domain.NotWritableFieldException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

/**
 * Utility class used to write a value into an Object.
 *
 */
public class ReflectionSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionSupport.class);

    public static Object invokeMethod(Object anObject, String method, Object ... params) {
        assertNotNull(anObject, "Object to invoke method on cannot be null!");
        try {
            Method m = null;
            if(params == null) {
                m = anObject.getClass().getMethod(method);
            } else {
                Class<?>[] classes = getParametersClasses(params);
                m = anObject.getClass().getMethod(method, classes);
            }
            return invokeMethod(anObject, m, params);
        } catch (NoSuchMethodException e) {
            throw new MethodInvocationException("Can't invoke method " + method + " on class " + anObject.getClass(), e);
        }
    }

    public static Class<?>[] getParametersClasses(Object[] params) {
        assertNotNull(params, "Parameters array cannot be null!");
        Class<?>[] classes = new Class<?>[ params.length ];
        int i = 0;
        for(Object obj : params) {
            assertNotNull(obj, "Cannot determine parameter type of null object");
            classes[i++] = obj.getClass();
        }
        return classes;
    }

    public static Object invokeMethod(Object anObject, Method m, Object ... params) {
        assertNotNull(anObject, "Object to invoke method on cannot be null!");
        assertNotNull(m, "Method to invoke cannot be null!");
        boolean accessible = m.isAccessible();
        if(!accessible) {
            m.setAccessible(true);
        }
        try {
            if (params == null) {
                return m.invoke(anObject);
            } else {
                return m.invoke(anObject, params);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new MethodInvocationException("Can't invoke method " + m.getName() + " on class " + anObject.getClass(), e);
        } finally {
            if(!accessible) {
                m.setAccessible(false);
            }
        }
    }

    public static void writeValue(Object anObject, String field, Object value) {
        Class<?> clazz = anObject.getClass();
        boolean success = false;
        try {
            tryInjectIntoField(anObject, field, value, clazz);
            success = true;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            LOGGER.warn("Can't inject field " + field + " for class " + clazz, e);
        }
        if(!success) {
            try {
                tryInvokeSetMethod(clazz, anObject, field, value);
                success = true;
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                LOGGER.warn("Can't invoke set method for field " + field + " on class " + clazz, e);
            }
        }
        if(!success) {
            throw new NotWritableFieldException("Can't write field <" +field +
                    "> on class <" + clazz + "> with value <" + value +
                    "> nor invoking set method, nor injecting directly into field");
        }
    }
    public static void writeValue(Object anObject, Field field, Object value) {
        Class<?> clazz = anObject.getClass();
        boolean success = false;
        try {
            tryInjectIntoField(anObject, field, value, clazz);
            success = true;
        } catch (IllegalAccessException e) {
            LOGGER.warn("Can't inject field " + field + " for class " + clazz, e);
        }
        if(!success) {
            try {
                tryInvokeSetMethod(clazz, anObject, field.getName(), value);
                success = true;
            } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
                LOGGER.warn("Can't invoke set method for field " + field + " on class " + clazz, e);
            }
        }
        if(!success) {
            throw new NotWritableFieldException("Can't write field <" +field +
                    "> on class <" + clazz + "> with value <" + value +
                    "> nor invoking set method, nor injecting directly into field");
        }
    }

    private static void tryInjectIntoField(Object anObject, String field, Object value, Class<?> clazz) throws IllegalAccessException, NoSuchFieldException {
        tryInjectIntoField(anObject, checkField(clazz, field), value, clazz);
    }

    private static void tryInjectIntoField(Object anObject, Field f, Object value, Class<?> clazz) throws IllegalAccessException {
        if(!isWritable(f)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Skip write on final field {}", f.getName());
            }
            return ;
        }
        boolean accessible = false;
        try {
            accessible = f.isAccessible();
            if( !accessible ) {
                f.setAccessible(true);
            }
            if(f.getType().isPrimitive()) {
                writePrimitive(f, anObject, value);
            } else {
                f.set(anObject, value);
            }
        } catch(IllegalAccessException e) {
            throw e;
        } finally {
            if( f!= null && !accessible ) {
                f.setAccessible(accessible);
            }
        }
    }

    private static void writePrimitive(Field primitiveField, Object target, Object value) throws IllegalAccessException {
        Class<?> primitive = primitiveField.getType();
        switch(primitive.getName()) {
            case "double" :
                primitiveField.set(target,
                        ((Double) value).doubleValue()
                );
                break;
            case "long" :
                primitiveField.set(target,
                        ((Long) value).longValue());
                break;
        }
    }

    private static void tryInvokeSetMethod(Class<?> clazz, Object aObject, String field, Object value) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field, aObject.getClass());
        propertyDescriptor.getWriteMethod().invoke(aObject, value);
    }

    public static Field checkField(Class<?> clazz, String k) throws NoSuchFieldException {
        return clazz.getDeclaredField(k);
    }

    public static Map<String, Field> readFields(Class<?> clazz) {
        return Arrays
                .stream(clazz.getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, Function.identity()));
    }

    public static boolean isWritable(Field f) {
        return !Modifier.isFinal(f.getModifiers());
    }

}
