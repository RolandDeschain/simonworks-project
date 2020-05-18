/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.Deserialize;
import org.simonworks.projects.conversion.Deserializer;
import org.simonworks.projects.conversion.Serializer;
import org.simonworks.projects.utils.CollectionUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class WebMethod {

    private final Method underlyingMethod;
    private String exposedName;
    private List<WebParam> pathParams;
    private List<WebParam> queryParams;
    private Serializer outputSerializer;

    public WebMethod(Method underlyingMethod) {
        this.underlyingMethod = underlyingMethod;
    }

    public void addPathParam(WebParam webParam) {
        if(pathParams == null) {
            pathParams = new ArrayList<>();
        }
        pathParams.add(
                Objects
                        .requireNonNull(webParam));
    }

    public void addQueryParam(WebParam webParam) {
        if(queryParams == null) {
            queryParams = new ArrayList<>();
        }
        queryParams.add(
                Objects
                        .requireNonNull(webParam));
    }

    public boolean hasParameters() {
        return CollectionUtils.isNotEmpty(pathParams) || CollectionUtils.isNotEmpty(queryParams);
    }

    public static class WebParam {
        private String paramName;
        private boolean isQueryParam;
        private boolean isPathParam;
        private Class<?> type;
        private int ordinal;
        private Deserializer inputDeserializer;

        private WebParam(String paramName, Class<?> type, int ordinal) {
            this.paramName = paramName;
            this.type = type;
            this.ordinal = ordinal;
        }

        public static WebParam queryParam(String paramName, Class<?> type, int ordinal) {
            WebParam wp = new WebParam(paramName, type, ordinal);
            wp.isQueryParam = true;
            return wp;
        }

        public static WebParam pathParam(String paramName, Class<?> type, int ordinal) {
            WebParam wp = new WebParam(paramName, type, ordinal);
            wp.isPathParam = true;
            return wp;
        }

        public static <T> WebParam body(Deserializer deserializer, Class<T> type) {
            WebParam wp = new WebParam("body", type, 1);
            wp.inputDeserializer = deserializer;
            return wp;
        }

        public String getParamName() {
            return paramName;
        }

        public Class<?> getType() {
            return type;
        }

        public int getOrdinal() {
            return ordinal;
        }

        public boolean isPathParam() {
            return isPathParam;
        }

        public boolean isQueryParam() {
            return isQueryParam;
        }

        public Deserializer getInputDeserializer() {
            return inputDeserializer;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", WebParam.class.getSimpleName() + "[", "]")
                    .add("paramName='" + paramName + "'")
                    .add("isQueryParam=" + isQueryParam)
                    .add("isPathParam=" + isPathParam)
                    .add("type=" + type)
                    .add("ordinal=" + ordinal)
                    .toString();
        }
    }

    public String getExposedName() {
        return exposedName;
    }

    public void setExposedName(String exposedName) {
        this.exposedName = exposedName;
    }

    public Method getUnderlyingMethod() {
        return underlyingMethod;
    }

    public List<WebParam> getPathParams() {
        return pathParams;
    }

    public List<WebParam> getQueryParams() {
        return queryParams;
    }

    public List<String> getVariableNames() {
        return pathParams == null ? null : pathParams
                .stream()
                .filter(WebParam::isPathParam)
                .map(WebParam::getParamName)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WebMethod.class.getSimpleName() + "[", "]")
                .add("underlyingMethod=" + underlyingMethod.getName())
                .add("exposedName=" + exposedName)
                .add("pathParams=" + pathParams)
                .add("queryParams=" + queryParams)
                .toString();
    }
}
