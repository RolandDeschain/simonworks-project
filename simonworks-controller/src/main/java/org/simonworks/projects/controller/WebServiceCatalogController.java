/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.controller;

import org.simonworks.projects.context.WebBeanContext;
import org.simonworks.projects.context.WebBeanInfo;
import org.simonworks.projects.context.annotation.InjectBeanContext;
import org.simonworks.projects.context.annotations.*;

@WebResource(
        name = "serviceCatalog",
        description = "Web Api that provides information about all the services exposed by the web application",
        version = @Version(major = 0, minor = 1, patch = 0, description = "first release"))
public class WebServiceCatalogController {

    @InjectBeanContext
    private WebBeanContext context;

    @MethodMapping(
            path = "/all-services"
    )
    public @Serialize(serializerName = "jsonSerializer") ServiceCatalogBean getAllServices() {
        ServiceCatalogBean result = new ServiceCatalogBean();
        context.beanNamesStream().forEach(s -> {
            WebBeanInfo mappingResource = context.getMappingResource(s);
            if(mappingResource.isWebResource()) {
                result.addWebBeanInfo(mappingResource);
            }
        });
        return result;
    }

    @MethodMapping(
            path = "/register-service/{serviceName}",
            verb = HttpVerb.POST
    )
    public void registerServices(@PathParam("serviceName") String serviceName) {
        System.out.println("Registering service " + serviceName);
    }
}
