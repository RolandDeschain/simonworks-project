/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link AbstractCompositeBeanContext} is a special implementation of BeanContext that can delegate bean recovery/store to a delegate {@link AbstractBeanContext}.
 */
public abstract class AbstractCompositeBeanContext extends AbstractBeanContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCompositeBeanContext.class);

    private AbstractBeanContext delegateContext;

    AbstractCompositeBeanContext(AbstractBeanContext parentContext) {
        this(parentContext.getBeanRegistry(), parentContext);
    }

    AbstractCompositeBeanContext(BeanRegistry beanRegistry, AbstractBeanContext parentContext) {
        super(beanRegistry);
        setDelegateContext(parentContext);
    }

    public AbstractBeanContext getDelegateContext() {
        return delegateContext;
    }

    public void setDelegateContext(AbstractBeanContext delegateContext) {
        this.delegateContext = delegateContext;
    }

    public BeanContext getNoopDelegateContext() { return NoopBeanContext.get(); }

    @Override
    protected BeanInfo getBeanInfo(String name) {
        BeanInfo result = getBeanRegistry().getBeanInfo(name);
        if(result == null) {
            result = getDelegateContext().getBeanRegistry().getBeanInfo(name);
        }
        if(LOGGER.isTraceEnabled()) {
            LOGGER.trace("getBeanInfo(<{}>) = <{}>", name, result);
        }
        return result;
    }

    @Override
    public void handleBeanAnnotations(Object bean, BeanInfo beanInfo) {
        super.handleBeanAnnotations(bean, beanInfo);
        getDelegateContext().handleBeanAnnotations(bean, beanInfo);
    }

    @Override
    protected void doHandleBeanAnnotations(Object bean, BeanInfo beanInfo) {
        getDelegateContext().doHandleBeanAnnotations(bean, beanInfo);
    }

    @Override
    protected Object get(BeanInfo beanInfo) {
        Object result = null;
        if(getDelegateContext() instanceof AbstractBeanContext) {
            result = super.searchInCache(beanInfo, getDelegateContext().getSingletonsCache());
        }
        if(result == null) {
            result = super.get(beanInfo);
        } else {
            if(LOGGER.isTraceEnabled()) {
                LOGGER.trace("Recovered object <{}> using BeanInfo <{}> from ", result, beanInfo);
            }
        }
        return result;
    }

    @Override
    protected void store(Object result, BeanInfo beanInfo) {
        super.store(result, beanInfo);
        getDelegateContext().store(result, beanInfo);
    }
}
