/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.context;

import org.simonworks.projects.factory.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link AbstractCompositeBeanContext} is a special implementation of BeanContext that can delegate bean recovery/store to a delegate {@link AbstractBeanContext}.
 */
public abstract class AbstractCompositeBeanContext extends AbstractBeanContext {

    private final Logger LOGGER = LoggerFactory.getLogger(AbstractCompositeBeanContext.class);

    private AbstractBeanContext delegateContext;

    AbstractCompositeBeanContext(AbstractBeanContext delegateContext,
                        String ... packagesToScan) {
        super(packagesToScan);
        setDelegateContext(delegateContext);
        setBeanFactory(delegateContext.getBeanFactory());
        setSingletonsCache(delegateContext.getSingletonsCache());
    }

    AbstractCompositeBeanContext(AbstractBeanContext parentContext,
                        BeanFactory beanFactory,
                        BeanRegistryProvider beanRegistryProvider,
                        SingletonsCache singletonsCache) {
        super(
                beanFactory,
                beanRegistryProvider,
                singletonsCache);
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

    protected Object get(BeanInfo beanInfo) {
        Object result = null;
        if(getDelegateContext() instanceof AbstractBeanContext) {
            AbstractBeanContext delegateContext = ((AbstractBeanContext) getDelegateContext());
            result = super.searchInCache(beanInfo, delegateContext.getSingletonsCache());
        }
        if(result == null) {
            result = super.get(beanInfo);
        }
        return result;
    }

    @Override
    protected void store(Object result, BeanInfo beanInfo) {
        super.store(result, beanInfo);
        getDelegateContext().store(result, beanInfo);
    }

    @Override
    protected SingletonsCache getDefaultSingletonCache() {
        return getDelegateContext().getDefaultSingletonCache();
    }

    @Override
    protected BeanFactory getDefaultBeanFactory() {
        return getDelegateContext().getDefaultBeanFactory();
    }
}
