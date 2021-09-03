/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.web;

import javax.servlet.http.HttpServletResponse;

/**
 * Instances of this interface contain different behaviours for http responses management
 *
 */
public interface ResponseProcessor {

    void writeResponse(HttpServletResponse resp, Object payload);
}
