package org.simonworks.projects.context;

@FunctionalInterface
public interface BeanRegistryProvider {

    BeanRegistry loadBeanRegistry();

}
