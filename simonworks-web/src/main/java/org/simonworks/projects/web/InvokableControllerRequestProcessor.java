/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.web;

import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.context.WebBeanContext;
import org.simonworks.projects.context.WebBeanInfo;
import org.simonworks.projects.context.WebMethod;
import org.simonworks.projects.context.annotation.InjectBeanContext;
import org.simonworks.projects.context.annotations.HttpVerb;
import org.simonworks.projects.conversion.Serializer;
import org.simonworks.projects.domain.MethodInvocationException;
import org.simonworks.projects.domain.ReflectionSupport;
import org.simonworks.projects.utils.CollectionUtils;
import org.simonworks.projects.conversion.StringConversionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;

@Singleton(name = "requestProcessor")
public class InvokableControllerRequestProcessor implements RequestProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvokableControllerRequestProcessor.class);
    private static final String CANT_PROCESS_REQUEST = "Cant process request. Exception occurred";

    @InjectBeanContext
    private WebBeanContext beanContext;

    @Override
    public String processRequest(HttpServletRequest request) {
        ResourceAndParameters resource = ResourceAndParameters.parse(request);
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Trying to invoke resource <{}>", resource);
        }
        WebBeanInfo wInfo = beanContext.getMappingResource(resource.getResource());
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("WebInfo for resource <{}> = <{}>", resource.getResource(), wInfo);
        }
        WebMethod methodToInvoke = wInfo.getMethod(
                HttpVerb.valueOf(request.getMethod()),
                resource.getMethod(),
                resource.getPathParams()
        );
        Objects.nonNull(methodToInvoke);
        if(methodToInvoke != null) {
            Object controller = beanContext.getBean(resource.getResource());
            Object result = null;
            try {
                if(methodToInvoke.hasParameters()) {
                    result = ReflectionSupport.invokeMethod(controller, methodToInvoke.getUnderlyingMethod(), prepareCall(
                                methodToInvoke,
                                resource,
                                request.getParameterMap()));
                } else {
                    result = ReflectionSupport.invokeMethod(controller, methodToInvoke.getUnderlyingMethod());
                }
                if(methodToInvoke.isOutputToBeSerialized()) {
                    Serializer serializer = beanContext.getBean(methodToInvoke.getSerializeBehaviour());
                    if(serializer != null) {
                        result = serializer.serialize(result);
                    } else result = String.valueOf(result);
                }

                return String.valueOf(result);
            } catch (MethodInvocationException e) {
                LOGGER.error(CANT_PROCESS_REQUEST, e);
            }
        }

        return null;
    }

    private Object[] prepareCall(WebMethod method, ResourceAndParameters resource, Map<String, String[]> parameterMap) {
        Object[] result = new Object[ method.getUnderlyingMethod().getParameters().length ];

        if(CollectionUtils.isNotEmpty(method.getPathParams())) {
            for (WebMethod.WebParam wp : method.getPathParams()) {
                result[wp.getOrdinal()] =
                        StringConversionUtils.convert(
                                wp.getType(),
                                resource.getPathParams().get(wp.getOrdinal()));
            }
        }

        if(CollectionUtils.isNotEmpty(method.getQueryParams())) {
            for(WebMethod.WebParam wp : method.getQueryParams()) {
                result[ wp.getOrdinal() ] =
                        StringConversionUtils.convert(
                                wp.getType(),
                                parameterMap.get( wp.getParamName() )[0]);
            }
        }
        return result;
    }
}
