package org.simonworks.projects.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClasspathBeanRegistryProviderImpl implements BeanRegistryProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathBeanRegistryProviderImpl.class);

    private List<String> packagesToScan = new ArrayList<>();

    public void addPackageToScan(String packageToScan) {
        if(LOGGER.isInfoEnabled()) {
            LOGGER.info("Adding package <{}> in autoscan-bean list", packageToScan);
        }
        packagesToScan.add(packageToScan);
    }

    @Override
    public BeanRegistry loadBeanRegistry() {
        BeanRegistry registry = new DefaultBeanRegistry();
        packagesToScan
                .forEach(packageName -> {
                    try {
                        ClassUtils
                                .getClasses(packageName)
                                .forEach(registry::registerBean);
                    } catch (ClassNotFoundException | IOException e) {
                        LOGGER.error("Cannot process package <" + packageName + ">", e);
                    }
                });
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Ready to use bean registry <{}>", registry);
        }
        return registry;
    }
}
