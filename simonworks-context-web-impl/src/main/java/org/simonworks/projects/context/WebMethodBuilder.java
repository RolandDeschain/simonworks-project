/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.Deserialize;
import org.simonworks.projects.context.annotations.MethodMapping;
import org.simonworks.projects.context.annotations.PathParam;
import org.simonworks.projects.utils.Assertions;
import org.simonworks.projects.utils.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class WebMethodBuilder {

    public static final String PATH_VARIABLE_START = "/{";
    public static final String PATH_VARIABLE_END = "}";

    private WebMethodBuilder() {}

    /**
     * Creates a {@link WebMethodBuilder} that takes data from {@link MethodMapping} in input
     * @see WebPathIterator
     */
    public static WebMethod buildWebMethod(Method underlyingMethod) {
        MethodMapping mapping = underlyingMethod.getAnnotation(MethodMapping.class);
        if(mapping == null) {
            return null;
        }
        WebMethod result = new WebMethod(underlyingMethod);
        WebPathIterator i = new WebPathIterator(mapping.path());
        StringBuilder exposedName = new StringBuilder();
        while(i.hasNext()) {
            String s = i.next();
            if(isPathVariable(s)) {
                result.addPathParam( createWebParam(s, underlyingMethod) );
                i.forEachRemaining(s1 -> result.addPathParam( createWebParam(s1, underlyingMethod) ));
            } else {
                exposedName.append(s);
            }
        }
        result.setExposedName(exposedName.toString());

        //if(underlyingMethod.getAnnotatedReturnType())

        return result;
    }

    private static boolean isPathVariable(String s) {
        return StringUtils.isNotEmpty(s)
                && s.startsWith(PATH_VARIABLE_START)
                && s.endsWith(PATH_VARIABLE_END);
    }

    private static WebMethod.WebParam createWebParam(String v, Method targetMethod) {
        Assertions.assertTrue(
                v.startsWith(PATH_VARIABLE_START)
                        && v.endsWith(PATH_VARIABLE_END),
                "Invalid path variable format " + v +
                        ". Path variable must start with \"/{\" and end with \"}\" strings");

        String m = v.substring(2, v.length() - 1);
        int i = 0;
        for(Parameter p : targetMethod.getParameters()) {
            if(p.isAnnotationPresent(PathParam.class)) {
                PathParam param = p.getAnnotation(PathParam.class);
                if(param.value().equals(m)) {
                    return WebMethod.WebParam.pathParam(param.value(), p.getType(), i);
                }
            }
            if(p.isAnnotationPresent(Deserialize.class)) {
                Deserialize deser = p.getAnnotation(Deserialize.class);
                return WebMethod.WebParam.body(deser.deserializerName(), p.getType());
            }
            i++;
        }
        return null;
    }

}
