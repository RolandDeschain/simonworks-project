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

package org.simonworks.projects.conversion.json;

import java.util.Arrays;

public class JsonValue<T> extends AbstractJsonElement<T> {

    public JsonValue(T value) {
        super(value);
    }

    static <T> JsonElement jsonNull(T ... t) {
        return new JsonValue("null") {
            @Override
            public Object getRepresentedValue() {
                return null;
            }

            @Override
            public String toString() {
                return "null";
            }
        };
    }
    static JsonElement<String> jsonString(String s) {
        return new JsonValue<String>(s) {
            @Override
            public String toString() {
                return new StringBuilder(DOUBLE_QUOTE)
                        .append(getRepresentedValue())
                        .append(DOUBLE_QUOTE)
                        .toString();
            }
        };
    }
    static JsonElement<Number> jsonNumber(Number n) {
        return new JsonValue<Number>(n);
    }
    static JsonElement<Boolean> jsonBoolean(Boolean b) {
        return new JsonValue<Boolean>(b);
    }
    static <T> JsonElement<JsonElement[]> jsonArray(JsonElement<T>[] a) {
        return new JsonValue<JsonElement[]>(a) {
            @Override
            public String toString() { return Arrays.toString(getRepresentedValue()); }
        };
    }

    public static JsonElement map(Object value) {
        JsonElement jsonValue = JsonValue.jsonNull();
        if(value != null) {
            Class<?> type = value.getClass();
            if(type.isArray()) {
                jsonValue = getJsonArray((Object[]) value);
            } else if(String.class.isAssignableFrom(type)) {
                jsonValue = JsonValue.jsonString((String) value);
            } else if(Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
                jsonValue = JsonValue.jsonBoolean((Boolean) value);
            } else if(Number.class.isAssignableFrom(type) || type.isPrimitive()) {
                jsonValue = JsonValue.jsonNumber((Number) value);
            } else {
                jsonValue = JsonObject.map(value);
            }
        }
        return jsonValue;
    }

    public static JsonElement getJsonArray(Object[] value) {
        JsonElement[] result = new JsonElement[value.length];

        for(int i = 0; i<value.length; i++) {
            result[i] = map(value[i]);
        }
        return JsonValue.jsonArray(result);
    }
}
