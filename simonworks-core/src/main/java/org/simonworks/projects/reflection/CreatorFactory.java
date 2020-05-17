/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.reflection;

import org.simonworks.projects.annotations.Singleton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory for Object constructors
 *
 */
@Singleton(name = "constructorsFactory")
public class CreatorFactory {

    private Map<Typed<?>, ObjectCreator<?>> creators = Collections.synchronizedMap(new HashMap<>());

    public <T> ObjectCreator<? extends T> getCreator(Typed<? super T> typed) {
        // First check in creators map
        ObjectCreator<?> creator = creators.get(typed);
        if( creator == null ) {
            // If creators map doesn't contain a specific creator for typed, lets create one
            creator = buildCreator(typed);
            // Then, store it in creators map
            creators.put(typed, creator);
        }
        return (ObjectCreator<? extends T>) creator;
    }

    private <T> ObjectCreator<T> buildCreator(Typed<? super T> typed) {
        return null;
    }

}
