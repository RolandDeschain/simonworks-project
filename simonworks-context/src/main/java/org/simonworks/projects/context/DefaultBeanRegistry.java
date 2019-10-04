package org.simonworks.projects.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

class DefaultBeanRegistry implements BeanRegistry {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultBeanRegistry.class);

    private Map<String, BeanInfo> registry = new ConcurrentHashMap<>(256);

    @Override
    public void registerBean(BeanInfo info) {
        assert info != null : "Cannot register null bean info";
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Registering bean info <{}>", info);
        }
        registry.put(info.getName(), info);
    }

    @Override
    public void unregisterBean(String name) {
        assert registry.containsKey(name) : "Bean info for name " + name + " are not available";
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Unregistering bean info for name <{}>", name);
        }
        registry.remove(name);
    }

    @Override
    public BeanInfo getBeanInfo(String name) {
        assert registry.containsKey(name) : "Bean info for name " + name + " are not available";
        return registry.get(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DefaultBeanRegistry.class.getSimpleName() + "[", "]")
                .add("registry=" + registry)
                .toString();
    }
}
