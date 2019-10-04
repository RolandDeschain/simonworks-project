package org.simonworks.projects.context;

import org.simonworks.projects.context.annotation.CompleteSetup;
import org.simonworks.projects.context.annotation.Dependency;
import org.simonworks.projects.context.annotation.InjectApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class BeanInfo {

    private final String name;

    private final Class<?> beanClass;

    /**
     * Map of bean dependencies. key is the name of a dependency bean, value if the metadata annotation
     */
    private Map<Field, Dependency> dependencies;
    /**
     * Special dependency for ApplicationContext
     */
    private Field injectableApplicationContext;
    /**
     * Method to be called after any other creation phase
     */
    private Method completeSetup;

    private Lifecycle lifecycle;


    enum Lifecycle {
        SINGLETON,
        PROTOTYPE
    }

    BeanInfo(String name, Class<?> beanClass, Lifecycle lifecycle) {
        assert name != null && name.length() != 0 : "Bean name cannot be null";
        assert beanClass != null : "Bean class cannot be null";
        this.name = name;
        this.beanClass = beanClass;
        setLifecycle(lifecycle);
        checkDependencies(beanClass);
    }

    private void checkDependencies(Class<?> clazz) {
        dependencies = Arrays
                .stream(clazz.getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(Dependency.class))
                .collect(Collectors.toMap(Function.identity(), field -> field.getAnnotation(Dependency.class)));

        injectableApplicationContext = Arrays
                .stream(clazz.getDeclaredFields())
                .filter(f ->
                        f.isAnnotationPresent(InjectApplicationContext.class))
                .findAny().orElse(null);

        completeSetup = Arrays
                .stream(clazz.getDeclaredMethods())
                .filter(m ->
                        m.isAnnotationPresent(CompleteSetup.class))
                .findAny().orElse(null);
    }

    public String getName() {
        return name;
    }

    Class<?> getBeanClass() {
        return beanClass;
    }

    Map<Field, Dependency> getDependencies() {
        return dependencies;
    }

    Field getInjectableApplicationContext() {
        return injectableApplicationContext;
    }

    Method getCompleteSetup() {
        return completeSetup;
    }

    private void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = Optional.of(lifecycle).orElse(Lifecycle.PROTOTYPE);
    }

    Lifecycle getLifecycle() {
        return lifecycle;
    }

   @Override
    public String toString() {
        return new StringJoiner(", ", BeanInfo.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("beanClass=" + beanClass)
                .add("dependencies=" + dependencies)
                .add("lifecycle=" + lifecycle)
                .toString();
    }
}
