/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.MethodMapping;
import org.simonworks.projects.context.annotations.QueryParam;
import org.simonworks.projects.context.annotations.Version;
import org.simonworks.projects.context.annotations.WebResource;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;

import static java.lang.String.valueOf;

/**
 * Defines the web resources exposure logic. Implementation of this class must use the information provided by {@link WebResource} and {@link MethodMapping}
 * annotations in order to define which will be the web resource exposure path and versioning behaviour.
 */
public interface WebResourceProcessor {

   static String versionAsString(Version version) {
        return new StringJoiner(".")
                .add(valueOf(version.major()))
                .add(valueOf(version.minor()))
                .add(valueOf(version.patch()))
                .toString();
    }

    /**
     * Uses the informations provided by input {@link WebResource} in order to calculate the exposure web path.
     * @param webResource
     *  The {@link WebResource} providing informations for the web resource
     */
    String mapWebResourcePath(WebResource webResource);

    default WebMethod mapMethod(Method method) {
        WebMethod webMethod = WebMethodBuilder.buildWebMethod(method);
        int ordinal = 0;
        for (Parameter parameter : method.getParameters()) {
            if (parameter.isAnnotationPresent(QueryParam.class)) {
                QueryParam qp = parameter.getAnnotation(QueryParam.class);
                webMethod.addQueryParam(
                        WebMethod.WebParam.queryParam(
                                qp.value(),
                                parameter.getType(),
                                ordinal));
            }
            ordinal++;
        }
        return webMethod;
    }

}
