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

import org.simonworks.projects.annotations.Prototype;
import org.simonworks.projects.conversion.DeserializationException;
import org.simonworks.projects.conversion.Deserializer;
import org.simonworks.projects.reflection.ReflectionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@link Deserializer} implementation for Json Strings
 */
@Prototype(name = "jsonDeserializer")
public class JsonDeserializer implements Deserializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDeserializer.class);

    private final Map<Class<?>, Map<String, Field>> fields = new HashMap<>();

    private void registerClass(Class<?> clazz) {
        fields.computeIfAbsent(clazz, aClass -> buildFieldsMap(aClass));
    }

    private Map<String, Field> buildFieldsMap(Class<?> clazz) {
        return Arrays
                .stream(clazz.getDeclaredFields())
                .collect(Collectors.toMap(field -> field.getName(), Function.identity()));
    }

    private Field getField(Class<?> clazz, String key) {
        return fields.get(clazz).get(key);
    }

    @Override
    public <O> O deserialize(String json, Class<O> clazz) throws DeserializationException {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("deserialize(json <{}>, Class <{}>)", json, clazz);
        }
        try {
            return (O) deserialize(new JsonCharArrayReader(json), clazz);
        } catch (DeserializationException e) {
            throw new DeserializationException("Invalid json " + json, e);
        }
    }

    public <O> O deserialize(JsonReader reader, Class<O> clazz) throws DeserializationException {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("deserialize(reader <{}>, Class <{}>)", reader, clazz);
        }
        O result = null;
        reader.skipWhiteSpaces();
        try {
            result = (O) readValue(reader, clazz);
        } catch (JsonReadException | JsonParseException e) {
            throw new DeserializationException("Cannot deserialize using reader " + reader, e);
        }
        return result;
    }

    private <O> O readObject(JsonReader reader, Class<O> clazz) throws JsonParseException, JsonReadException {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reading object from class <{}>", clazz);
        }
        registerClass(clazz);
        reader.expected(123);
        reader.nextChar();
        O result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new JsonParseException("Can't create new instance of class " + clazz);
        }
        while(reader.actual() != 125) {
            if(reader.actual() == 44) {
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Continuing with next couple...");
                }
                reader.nextChar();
            }
            String key = Parsers.parse(reader, String.class);
            Field f = getField(clazz, key);
            if(f == null) {
                throw new JsonParseException("Field  " + key + " not writable for class " + clazz);
            }
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Read key <{}>", key);
            }
            reader.skipWhiteSpaces();
            reader.expected(58);
            reader.nextChar();
            Object value = readValue(reader, f.getType());
            if(value != null) {
                ReflectionSupport.writeValue(result, f, value);
            }
            reader.skipWhiteSpaces();
        }
        reader.expected(125);
        reader.nextChar();
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Object completed!");
        }
        return result;
    }

    private <O> O readValue(JsonReader reader, Class<O> clazz) throws JsonParseException, JsonReadException {
        O value = null;
        if(!isNextNull(reader)) {
            Type type = (Type) clazz;
            if(type instanceof ParameterizedType) {
                value = (O) readList(reader, clazz);
            } else if(type instanceof GenericArrayType) {
                value = (O) readArray(reader, (GenericArrayType) type);
            }
            else if(clazz.isArray()) {
                value = (O) readArray(reader, clazz.getComponentType());
            }
            else {
                // value is not null
                value = Parsers.parse(reader, clazz);
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Read value <{}>",value);
                }
                if(value == null) {
                    value = readObject(reader, clazz);
                }
            }
        }
        return value;
    }

    private <V> List<V> readList(JsonReader reader, Type type) throws JsonReadException, JsonParseException {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reading list of type <{}>", type);
        }
        List<V> result = new ArrayList<>();
        reader.expected(91);
        while(true) {
            V item = ((Class<V>)type).cast(readValue(reader, (Class<V>)type));
            result.add( item );
            reader.skipWhiteSpaces();
            if(reader.actual() == 44) {
                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Continuing with next couple...");
                }
                reader.nextChar();
            } else {
                reader.expected(93);
                break;
            }
        }

        reader.nextChar();
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Array completed!");
        }
        return result;
    }

    private <V> V[] readArray(JsonReader reader, GenericArrayType type) throws JsonParseException, JsonReadException {
        List<V> vs = readList(reader, type.getGenericComponentType());
        V[] result = (V[]) Array.newInstance(((Class<V>)(Type)type), 0);
        return vs.toArray(result);
    }

    private <V> V[] readArray(JsonReader reader, Class<V> aClass) throws JsonReadException, JsonParseException {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("Reading array of clazz <{}>", aClass);
        }
        List<V> vs = readList(reader, aClass);
//        V[] result = (V[]) Array.newInstance(aClass, vs.size());
//        int i = 0;
//        for(V v : vs) {
//            Array.set(result, i++, v);
//        }
        V[] result = (V[]) Array.newInstance(aClass, 0);
        return vs.toArray(result);
    }

    private boolean isNextNull(JsonReader reader) {
        reader.skipWhiteSpaces();
        if(reader.actual() != 'n' && reader.actual() != 'N') {
            return false;
        }
        int count = JsonParser.matchesNext(reader, "null");
        if(count != "null".length()) {
            reader.unread(count);
            return false;
        }
        return true;
    }

}
