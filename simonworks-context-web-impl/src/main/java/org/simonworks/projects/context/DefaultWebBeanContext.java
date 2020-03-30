/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

/**
 * {@link BeanContext} implementation able to manage web annotations for beans
 * (ie {@link org.simonworks.projects.context.annotations.WebResource},
 * {@link org.simonworks.projects.context.annotations.MethodMapping}, {@link org.simonworks.projects.context.annotations.PathParam},
 * {@link org.simonworks.projects.context.annotations.QueryParam}, {@link org.simonworks.projects.context.annotations.Version})
 */
public class DefaultWebBeanContext extends DefaultBeanContext implements WebBeanContext {

    public DefaultWebBeanContext(BeanRegistry beanRegistry) {
        super(beanRegistry);
    }

    @Override
    public BeanContext self() {
        return this;
    }

    @Override
    public WebBeanInfo getMappingResource(String resource) {
        WebBeanInfo result = null;
        BeanInfo beanInfo = getBeanRegistry().getBeanInfo(resource);
        if(beanInfo instanceof WebBeanInfo) {
            result = (WebBeanInfo) beanInfo;
        }
        return result;
    }
}
