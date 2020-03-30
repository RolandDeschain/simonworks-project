/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.simonworks.projects.domain.DataMapper;
import org.simonworks.projects.utils.Assertions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static org.simonworks.projects.utils.Assertions.*;
import static org.simonworks.projects.utils.StringUtils.isNotEmpty;

/**
 * Object representing a Json object. Each {@link JsonElement} is a list of key-value couples.
 * Each key is always of type {@link String}, while each value is one of each each possible {@link JsonValue} implementations.
 * {@link JsonObject} adheres to Json specification that can be found <a href="https://www.json.org/json-en.html">here</a>
 *
 * @param <T>
 *           Il tipo di oggetto rappresentato da questo JsonObject
 */
public class JsonObject<T> extends AbstractJsonElement<T> {

    public static final String LEFT_BRACE = "{ ";
    public static final String RIGHT_BRACE = " }";
    public static final String COLON = " : ";

    private Map<String, JsonElement> couples;

    public JsonObject(T representedValue) {
        this(representedValue, new HashMap<>());
    }

    public JsonObject(T representedValue, Map<String, JsonElement> couples) {
        super(representedValue);
        assertNotNull(couples, "Can't instantiate a JsonObject with a null map of couples");
        this.couples = couples;
    }

    public JsonElement get(String key) {
        return couples.get(key);
    }

    public void put(String key, JsonElement value) {
        assertTrue(isNotEmpty(key), "Key cannot be null!");
        couples.put(key, value);
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", LEFT_BRACE, RIGHT_BRACE);
        couples.entrySet()
                .forEach(e -> sj.add(e.getKey() + COLON + e.getValue().toString()));
        return sj.toString();
    }

    public static <T> JsonObject<T> map(Object o) {
        JsonObject result = new JsonObject(o);
        Arrays
                .stream(o.getClass().getDeclaredFields())
                .forEach(f -> accumulate(result, o, f));
        return result;
    }

    public static void accumulate(JsonObject result, Object o, Field f) {
        boolean accessible = f.isAccessible();
        try {
            f.setAccessible(true);
            JsonElement jsonValue = JsonValue.map(f.get(o));
            result.put(f.getName(), jsonValue);
        } catch (IllegalAccessException e) {
            //TODO
            e.printStackTrace();
        } finally {
            f.setAccessible(accessible);
        }
    }
}
