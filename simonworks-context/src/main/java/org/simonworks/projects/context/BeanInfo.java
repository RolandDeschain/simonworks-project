package org.simonworks.projects.context;

import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.annotations.Singleton;
import org.simonworks.projects.context.annotation.CompleteSetup;
import org.simonworks.projects.context.annotation.Dependency;
import org.simonworks.projects.context.annotation.InjectBeanContext;
import org.simonworks.projects.reflection.Typed;
import org.simonworks.projects.utils.StringUtils;
import org.springframework.context.ApplicationContext;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class BeanInfo implements Serializable {

    private List<String> aliases;

    private transient Typed<?> type;

    /**
     * Map of bean dependencies. key is the name of a dependency bean, value if the metadata annotation
     */
    private transient Map<Field, Dependency> dependencies;
    /**
     * Special dependency for BeanContext
     */
    private transient Field injectableBeanContext;
    /**
     * Method to be called after any other creation phase
     */
    private transient Method completeSetup;

    private Lifecycle lifecycle;

    enum Lifecycle {
        SINGLETON,
        PROTOTYPE
    }

    BeanInfo(Class<?> clazz) {
        this( new Typed(clazz) );
    }

    BeanInfo(Typed<?> type) {
        this.type = Objects.requireNonNull(type, "Bean class cannot be null");
        lifecycle = initLifecycle();
        initAliases();
        checkDependencies();
    }

    protected Lifecycle initLifecycle() {
        Lifecycle result = null;
        if (type.getRawType().isAnnotationPresent(Singleton.class)) {
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
        Prototype prototype = type.getRawType().getAnnotation(Prototype.class);
        if(prototype != null) {
            result.add(prototype.name());
        } else {
            Singleton singleton = type.getRawType().getAnnotation(Singleton.class);
            if(singleton != null && StringUtils.isNotEmpty(singleton.name())) {
                result.add(singleton.name());
            }
        }
        return result;
    }

    private List<String> resolveBeanAliasesUsingClassName() {
        List<String> result = new ArrayList<>();
        String name = type.getRawType().getName();
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
                .stream(type.getRawType().getDeclaredFields())
                .filter(field ->
                        field.isAnnotationPresent(Dependency.class))
                .collect(Collectors.toMap(Function.identity(), field -> field.getAnnotation(Dependency.class)));

        injectableBeanContext = Arrays
                .stream(type.getRawType().getDeclaredFields())
                .filter(f ->
                        f.isAnnotationPresent(InjectBeanContext.class))
                .findAny().orElse(null);

        completeSetup = Arrays
                .stream(type.getRawType().getDeclaredMethods())
                .filter(m ->
                        m.isAnnotationPresent(CompleteSetup.class))
                .findAny().orElse(null);
    }

    public List<String> aliases() {
        return aliases;
    }

    public Typed<?> getType() {
        return type;
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
                .add("type=" + type.getRawType())
                .add("dependencies=" + dependencies)
                .add("lifecycle=" + lifecycle)
                .toString();
    }
}
