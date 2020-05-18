/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 *  Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.webapp;

import org.simonworks.projects.context.BeanRegistryProvider;
import org.simonworks.projects.context.DefaultWebBeanContext;
import org.simonworks.projects.context.WebBeanContext;
import org.simonworks.projects.context.WebBeanRegistryProvider;
import org.simonworks.projects.web.RequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimonWorksContextServlet extends HttpServlet {

    private static final String REQUEST_OF_CLASS = "Request <{}> of class <{}> ";
    private static final String CANT_PROCESS_REQUEST = "Cant process request. Exception occurred";

    private RequestProcessor requestProcessor;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimonWorksContextServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        String packagesToScan = config.getInitParameter("packagesToScan");
        String[] packages = packagesToScan.split(",");
        BeanRegistryProvider webProvider = new WebBeanRegistryProvider(packages);
        WebBeanContext beanContext = new DefaultWebBeanContext(webProvider.loadBeanRegistry());
        requestProcessor = beanContext.getBean("requestProcessor");
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Object result = requestProcessor.processRequest(req);
        resp.getOutputStream().print(result.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doGet ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            processRequest(req, resp);
        } catch(IOException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doPost ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            processRequest(req, resp);
        } catch(IOException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doPut ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            super.doPut(req, resp);
        } catch(IOException | ServletException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doHead ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            super.doHead(req, resp);
        } catch(IOException | ServletException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doOptions ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            super.doOptions(req, resp);
        } catch(IOException | ServletException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doDelete ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            super.doDelete(req, resp);
        } catch(IOException | ServletException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("---------- doTrace ---------------------");
            LOGGER.trace(REQUEST_OF_CLASS, req, req.getClass());
            debugRequest(req);
        }
        try {
            super.doTrace(req, resp);
        } catch(IOException | ServletException e) {
            LOGGER.error(CANT_PROCESS_REQUEST, e);
        }
    }

    private void debugRequest(HttpServletRequest req) {
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("getPathInfo <{}>", req.getPathInfo());
            LOGGER.trace("getServletPath <{}>", req.getServletPath());
            LOGGER.trace("getAuthType <{}>", req.getAuthType());
            LOGGER.trace("getContextPath <{}>", req.getContextPath());
            LOGGER.trace("getCookies <{}>", req.getCookies());
            LOGGER.trace("getHttpServletMapping <{}>", req.getHttpServletMapping());
            LOGGER.trace("getHeaderNames <{}>", req.getHeaderNames());
            LOGGER.trace("getMethod <{}>", req.getMethod());
            try {
                LOGGER.trace("getParts <{}>", req.getParts());
            } catch(Exception e) {
                LOGGER.error("getParts ", e);
            }
            LOGGER.trace("getPathTranslated <{}>", req.getPathTranslated());
            LOGGER.trace("getQueryString <{}>", req.getQueryString());
            LOGGER.trace("getRemoteUser <{}>", req.getRemoteUser());
            LOGGER.trace("getRequestURI <{}>", req.getRequestURI());
            LOGGER.trace("getRequestURL <{}>", req.getRequestURL());
            LOGGER.trace("getSession <{}>", req.getSession());
            LOGGER.trace("getTrailerFields <{}>", req.getTrailerFields());
            LOGGER.trace("getUserPrincipal <{}>", req.getUserPrincipal());
            LOGGER.trace("isRequestedSessionIdFromCookie <{}>", req.isRequestedSessionIdFromCookie());
            LOGGER.trace("isRequestedSessionIdFromURL <{}>", req.isRequestedSessionIdFromURL());
            LOGGER.trace("isRequestedSessionIdValid <{}>", req.isRequestedSessionIdValid());
            LOGGER.trace("isTrailerFieldsReady <{}>", req.isTrailerFieldsReady());
            LOGGER.trace("isAsyncStarted <{}>", req.isAsyncStarted());
            LOGGER.trace("isAsyncSupported <{}>", req.isAsyncSupported());
            if(req.isAsyncStarted()) {
                LOGGER.trace("getAsyncContext <{}>", req.getAsyncContext());
            }
            LOGGER.trace("getAttributeNames <{}>", req.getAttributeNames());
            LOGGER.trace("getCharacterEncoding <{}>", req.getCharacterEncoding());
            LOGGER.trace("getContentLength <{}>", req.getContentLength());
            LOGGER.trace("getContentLengthLong <{}>", req.getContentLengthLong());
            LOGGER.trace("getContentType <{}>", req.getContentType());
            LOGGER.trace("getDispatcherType <{}>", req.getDispatcherType());
            LOGGER.trace("getLocalAddr <{}>", req.getLocalAddr());
            LOGGER.trace("getLocale <{}>", req.getLocale());
            LOGGER.trace("getLocales <{}>", req.getLocales());
            LOGGER.trace("getLocalName <{}>", req.getLocalName());
            LOGGER.trace("getLocalPort <{}>", req.getLocalPort());
            LOGGER.trace("getParameterMap <{}>", req.getParameterMap());
            LOGGER.trace("getParameterNames <{}>", req.getParameterNames());
            LOGGER.trace("getProtocol <{}>", req.getProtocol());
            LOGGER.trace("getRemoteAddr <{}>", req.getRemoteAddr());
            LOGGER.trace("getRemoteUser <{}>", req.getRemoteUser());
            LOGGER.trace("getRemoteHost <{}>", req.getRemoteHost());
            LOGGER.trace("getRemotePort <{}>", req.getRemotePort());
            LOGGER.trace("getScheme <{}>", req.getScheme());
            LOGGER.trace("getServerName <{}>", req.getServerName());
            LOGGER.trace("getServerPort <{}>", req.getServerPort());
            LOGGER.trace("getServletContext <{}>", req.getServletContext());
            LOGGER.trace("isSecure <{}>", req.isSecure());
        }
    }

}
