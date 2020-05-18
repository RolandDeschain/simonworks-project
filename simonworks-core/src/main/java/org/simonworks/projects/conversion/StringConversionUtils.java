/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.conversion;

import org.simonworks.projects.domain.DataMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StringConversionUtils {

    private static Map<Class<?>, DataMapper<String, ?>> mappers;
    static {
        Map<Class<?>, DataMapper<String, ?>> m = new ConcurrentHashMap<>();
        m.put(Integer.class, Integer::valueOf);
        m.put(int.class, Integer::valueOf);
        m.put(Long.class, Long::valueOf);
        m.put(long.class, Long::valueOf);
        m.put(String.class, o -> o);
        m.put(Boolean.class, Boolean::valueOf);
        m.put(boolean.class, Boolean::valueOf);
        m.put(Double.class, Double::valueOf);
        m.put(double.class, Double::valueOf);
        m.put(Date.class, o -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            try {
                return sdf.parse(o);
            } catch (ParseException e) {
                return null;
            }
        });
        mappers = Collections.unmodifiableMap(m);
    }

    private StringConversionUtils() {}

    public static <T> T convert(Class<T> toClass, String source) {
        return (T) mappers.get(toClass).map(source);
    }
}
