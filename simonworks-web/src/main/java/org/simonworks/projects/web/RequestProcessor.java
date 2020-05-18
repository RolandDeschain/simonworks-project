/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Defines on object to process Requests coming to a {@link javax.servlet.Servlet}
 */
public interface RequestProcessor {

    <T> T processRequest(HttpServletRequest request);
}
