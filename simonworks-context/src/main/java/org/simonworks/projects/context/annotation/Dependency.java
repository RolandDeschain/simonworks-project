package org.simonworks.projects.context.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dependency {

    String beanName();

    String afterInjectionMethod() default "";
}
