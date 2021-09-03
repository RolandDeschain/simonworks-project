/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.web;

import org.simonworks.projects.context.WebPathIterator;
import org.simonworks.projects.utils.Assertions;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

class ResourceAndParameters {

    private String resource;
    private String method;
    private List<String> pathParams;

    private ResourceAndParameters(String resource, String method) {
        this.resource = resource;
        this.method = method;
    }

    private void addPathParam(String pathParam) {
        if(pathParam.length() != 1) {
            if (this.pathParams == null) {
                this.pathParams = new ArrayList<>();
            }
            this.pathParams.add( pathParam.substring(1) );
        }
    }

    public static ResourceAndParameters parse(HttpServletRequest request) {
        WebPathIterator it = new WebPathIterator(request.getPathInfo());
        //version
        Assertions.assertTrue(it.hasNext(), "Resource version not present");
        StringBuilder resourceBuilder = new StringBuilder(it.next());
        //resource
        Assertions.assertTrue(it.hasNext(), "Resource name not present");
        resourceBuilder.append(it.next());
        //method
        Assertions.assertTrue(it.hasNext(), "Resource method not present");
        ResourceAndParameters rap = new ResourceAndParameters(resourceBuilder.toString(), it.next());
        while(it.hasNext()) {
            rap.addPathParam(it.next());
        }
        return rap;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ResourceAndParameters.class.getSimpleName() + "[", "]")
                .add("resource='" + resource + "'")
                .add("method='" + method + "'" )
                .add("pathParams=" + pathParams)
                .toString();
    }

    public String getResource() {
        return resource;
    }

    public List<String> getPathParams() {
        return pathParams;
    }

    public String getMethod() {
        return method;
    }
}
