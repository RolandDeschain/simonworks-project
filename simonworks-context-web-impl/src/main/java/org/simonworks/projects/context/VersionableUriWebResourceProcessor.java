/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.context.annotations.WebResource;

/**
 *
 */
public class VersionableUriWebResourceProcessor implements WebResourceProcessor {

    /**
     * Uses the informations provided by input {@link WebResource} in order to calculate the exposure web path.
     *
     * @param webResource The {@link WebResource} providing informations for the web resource
     */
    @Override
    public String mapWebResourcePath(WebResource webResource) {
        StringBuilder sb = new StringBuilder("/v")
                .append(WebResourceProcessor.versionAsString(webResource.version()));
        if(!webResource.name().contains("/")) {
            sb.append('/');
        }
        return sb.append(webResource.name())
                .toString();
    }

}
