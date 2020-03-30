/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.*;
import org.simonworks.projects.utils.Assertions;
import org.simonworks.projects.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 * This BeanInfo implementations is used to represent  web annotations for a {@link Class}.
 * @see org.simonworks.projects.context.annotations.HttpVerb
 * @see org.simonworks.projects.context.annotations.WebResource
 */
public class WebBeanInfo extends BeanInfo {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebBeanInfo.class);

    private Version version;
    private WebResource webResource;
    private String webResourcePath;

    private Map<HttpVerb, List<org.simonworks.projects.context.WebMethod>> webMethodMappings;

    WebBeanInfo(Class<?> clazz, WebResourceProcessor webResourceProcessor) {
        super(clazz);
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Creating web bean info for class <{}>", clazz);
        }
        Assertions.assertNotNull(webResourceProcessor, "Web resource processor cannot be null");
        this.webResource = clazz.getAnnotation(WebResource.class);
        if( webResource != null ) {
            this.version = requireNonNull(webResource.version(), "Version cannot be null");
            webResourcePath = webResourceProcessor.mapWebResourcePath(webResource);
            aliases().add(webResourcePath);
            extractMethodAnnotations(webResourceProcessor);
        }
    }

    public org.simonworks.projects.context.WebMethod getMethod(HttpVerb verb, String method) {
        return getMethod(verb, method, null);
    }

    public org.simonworks.projects.context.WebMethod getMethod(HttpVerb verb, String method, List<String> pathVariables) {
        org.simonworks.projects.context.WebMethod result = null;
        List<org.simonworks.projects.context.WebMethod> webMethods = webMethodMappings.get(verb);
        if(webMethods != null) {
            Optional<org.simonworks.projects.context.WebMethod> optional = StreamSupport
                    .stream(webMethods.spliterator(), false)
                    .filter(webMethod -> webMethod.getExposedName().equals(method))
                    .filter(((Predicate<WebMethod>)
                            webMethod -> CollectionUtils.bothNull(pathVariables, webMethod.getVariableNames()))
                            .or(webMethod -> CollectionUtils.sameSize(pathVariables, webMethod.getVariableNames()))
                    )

                    .findFirst();
            if(optional.isPresent()) {
                result = optional.get();
            }
        }
        return result;
    }

    @Override
    protected Lifecycle initLifecycle() {
        return Lifecycle.SINGLETON;
    }

    private void extractMethodAnnotations(WebResourceProcessor webResourceProcessor) {
        webMethodMappings = Arrays
                .stream(super.getBeanClass().getMethods())
                .filter(method -> method.isAnnotationPresent(MethodMapping.class))
                .collect(Collectors.groupingBy(
                        m -> m.getAnnotation(MethodMapping.class).verb(),
                        Collectors.mapping(
                                webResourceProcessor::mapMethod,
                                Collectors.toList())));
    }

    public String getWebResourcePath() {
        return webResourcePath;
    }

    public Version getVersion() {
        return version;
    }

    public WebResource getWebResource() {
        return webResource;
    }

    public Map<HttpVerb, List<WebMethod>> getWebMethodMappings() {
        return webMethodMappings;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WebBeanInfo.class.getSimpleName() + "[", "]")
                .add("webResourcePath='" + webResourcePath + "'")
                .add("webMethodMappings=" + webMethodMappings)
                .add("Other informations=" + super.toString())
                .toString();
    }
}
