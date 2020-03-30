package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.context.annotation.CompleteSetup;
import org.simonworks.projects.context.annotation.Dependency;
import org.simonworks.projects.context.annotation.InjectBeanContext;
import org.simonworks.projects.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class BeanInfo {

    private List<String> aliases;

    private Class<?> beanClass;

    /**
     * Map of bean dependencies. key is the name of a dependency bean, value if the metadata annotation
     */
    private Map<Field, Dependency> dependencies;
    /**
     * Special dependency for BeanContext
     */
    private Field injectableBeanContext;
    /**
     * Method to be called after any other creation phase
     */
    private Method completeSetup;

    private Lifecycle lifecycle;

    enum Lifecycle {
        SINGLETON,
        PROTOTYPE
    }

    BeanInfo(Class<?> clazz) {
        fromClass(clazz);
        lifecycle = initLifecycle();
        initAliases();
        checkDependencies();
    }

    protected void fromClass(Class<?> clazz) {
        this.beanClass = Objects.requireNonNull(clazz, "Bean class cannot be null");
    }

    protected Lifecycle initLifecycle() {
        Lifecycle result = null;
        if (beanClass.isAnnotationPresent(Singleton.class)) {
            result = BeanInfo.Lifecycle.SINGLETON;
        } else {
            result = BeanInfo.Lifecycle.PROTOTYPE;
        }
        return result;
    }

    private void initAliases() {
        aliases = resolveBeanAliasesUsingAnnotations();
        if (aliases == null || aliases.isEmpty()) {
            aliases = resolveBeanAliasesUsingClassName();
        }
    }

    protected List<String> resolveBeanAliasesUsingAnnotations() {
        List<String> result = new ArrayList<>();
        Prototype prototype = beanClass.getAnnotation(Prototype.class);
        if(prototype != null) {
            result.add(prototype.name());
        } else {
            Singleton singleton = beanClass.getAnnotation(Singleton.class);
            if(singleton != null && StringUtils.isNotEmpty(singleton.name())) {
                result.add(singleton.name());
            }
        }
        return result;
    }

    private List<String> resolveBeanAliasesUsingClassName() {
        List<String> result = new ArrayList<>();
        String name = beanClass.getName();
        name = name.substring(name.lastIndexOf('.') + 1);
        char[] chars = name.toCharArray();
        StringJoiner joiner = new StringJoiner("");
        for(char c : chars) {
            if(Character.isUpperCase(c)) {
                joiner.add("-" + Character.toLowerCase(c));
            } else {
                joiner.add(String.valueOf(c));
            }
        }
        result.add(joiner.toString().substring(1));
        return result;
    }

    private void checkDependencies() {
        dependencies = Arrays
                .stream(beanClass.getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(Dependency.class))
                .collect(Collectors.toMap(Function.identity(), field -> field.getAnnotation(Dependency.class)));

        injectableBeanContext = Arrays
                .stream(beanClass.getDeclaredFields())
                .filter(f ->
                        f.isAnnotationPresent(InjectBeanContext.class))
                .findAny().orElse(null);

        completeSetup = Arrays
                .stream(beanClass.getDeclaredMethods())
                .filter(m ->
                        m.isAnnotationPresent(CompleteSetup.class))
                .findAny().orElse(null);
    }

    public List<String> aliases() {
        return aliases;
    }

    Class<?> getBeanClass() {
        return beanClass;
    }

    Map<Field, Dependency> getDependencies() {
        return dependencies;
    }

    Field getInjectableBeanContext() {
        return injectableBeanContext;
    }

    Method getCompleteSetup() {
        return completeSetup;
    }

    Lifecycle getLifecycle() {
        return lifecycle;
    }

   @Override
    public String toString() {
        return new StringJoiner(", ", BeanInfo.class.getSimpleName() + "[", "]")
                .add("aliases=" + aliases + "'")
                .add("beanClass=" + beanClass)
                .add("dependencies=" + dependencies)
                .add("lifecycle=" + lifecycle)
                .toString();
    }
}
