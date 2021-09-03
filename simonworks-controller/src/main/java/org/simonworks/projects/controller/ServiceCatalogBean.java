/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.controller;

import org.simonworks.projects.context.WebBeanInfo;
import org.simonworks.projects.model.AbstractModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ServiceCatalogBean extends AbstractModel<String> implements Iterable<WebBeanInfo> {

    private List<WebBeanInfo> services = new ArrayList<>();

    public boolean addWebBeanInfo(WebBeanInfo wbi) {
        return services.add(wbi);
    }

    @Override
    public Iterator<WebBeanInfo> iterator() {
        return services.iterator();
    }
}
