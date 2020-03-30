/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.WebResource;
import org.simonworks.projects.utils.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebBeanRegistry extends MapBeanRegistry {

    private WebResourceProcessor webResourceProcessor;

    @Override
    protected boolean isDiscoverableClass(Class<?> clazz) {
        return super.isDiscoverableClass(clazz) || clazz.isAnnotationPresent(WebResource.class);
    }

    @Override
    public WebBeanInfo createBeanInfo(Class<?> clazz) {
        Assertions.assertNotNull(webResourceProcessor, "Web Resource processor cannot be null!");
        return new WebBeanInfo(clazz, webResourceProcessor);
    }

    public void setWebResourceProcessor(WebResourceProcessor webResourceProcessor) {
        this.webResourceProcessor = webResourceProcessor;
    }

    public WebResourceProcessor getWebResourceProcessor() {
        return webResourceProcessor;
    }
}
