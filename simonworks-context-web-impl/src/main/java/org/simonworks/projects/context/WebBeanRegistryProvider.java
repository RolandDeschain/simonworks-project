/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

public class WebBeanRegistryProvider extends MapBeanRegistryProvider {

    private WebResourceProcessor webResourceProcessor;

    public WebBeanRegistryProvider(String... packagesToScan) {
        this(new VersionableUriWebResourceProcessor(), packagesToScan);
    }

    public WebBeanRegistryProvider(WebResourceProcessor webResourceProcessor, String... packagesToScan) {
        super(packagesToScan);
        this.webResourceProcessor = webResourceProcessor;
    }

    @Override
    public BeanRegistry createEmptyBeanRegistry() {
        WebBeanRegistry registry = new WebBeanRegistry();
        registry.setWebResourceProcessor(webResourceProcessor);
        return registry;
    }

    public void setWebResourceProcessor(WebResourceProcessor webResourceProcessor) {
        this.webResourceProcessor = webResourceProcessor;
    }

    public WebResourceProcessor getWebResourceProcessor() {
        return webResourceProcessor;
    }
}
