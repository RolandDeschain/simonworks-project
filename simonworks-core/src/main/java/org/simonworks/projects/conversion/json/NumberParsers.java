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

import java.util.function.Function;

abstract class NumberParsers<O extends Number> implements JsonParser<O> {

    protected abstract Function<String, O> applyConversion();

    protected abstract String getTypeDescr();

    @Override
    public O parse(JsonReader reader) throws JsonParseException {
        String s = null;
        int index = reader.index();
        try {
            reader.skipWhiteSpaces();
            s = reader.readUntil(JsonReader.valueSeparators.negate());
            return applyConversion().apply(s);
        } catch (JsonReadException | NumberFormatException e) {
            reader.unread(reader.index() - index);
            throw new JsonParseException("Invalid " + getTypeDescr() + " representation " + s);
        }
    }

    static class JsonDoubleParser extends NumberParsers<Double> {
        @Override public boolean canProduce(Class<?> clazz) {
            return Double.class == clazz || double.class == clazz;
        }
        @Override protected Function<String, Double> applyConversion() {
            return Double::parseDouble;
        }
        @Override protected String getTypeDescr() {
            return "double";
        }
    }

    static class JsonIntegerParser extends NumberParsers<Integer> {
        @Override protected Function<String, Integer> applyConversion() {
            return Integer::parseInt;
        }
        @Override protected String getTypeDescr() {
            return "int";
        }
        @Override public boolean canProduce(Class<?> clazz) {
            return Integer.class == clazz || int.class == clazz;
        }
    }

    static class JsonByteParser extends NumberParsers<Byte> {
        @Override protected Function<String, Byte> applyConversion() {
            return Byte::parseByte;
        }
        @Override protected String getTypeDescr() {
            return "byte";
        }
        @Override public boolean canProduce(Class<?> clazz) {
            return Byte.class == clazz || byte.class == clazz;
        }
    }

    static class JsonLongParser extends NumberParsers<Long> {
        @Override protected Function<String, Long> applyConversion() {
            return Long::parseLong;
        }
        @Override protected String getTypeDescr() {
            return "long";
        }
        @Override public boolean canProduce(Class<?> clazz) {
            return Long.class == clazz || long.class == clazz;
        }
    }

    static class JsonFloatParser extends NumberParsers<Float> {
        @Override protected Function<String, Float> applyConversion() {
            return Float::parseFloat;
        }
        @Override protected String getTypeDescr() {
            return "float";
        }
        @Override public boolean canProduce(Class<?> clazz) {
            return Float.class == clazz || float.class == clazz;
        }
    }

    static class JsonShortParser extends NumberParsers<Short> {
        @Override protected Function<String, Short> applyConversion() {
            return Short::parseShort;
        }
        @Override protected String getTypeDescr() {
            return "short";
        }
        @Override public boolean canProduce(Class<?> clazz) {
            return Short.class == clazz || short.class == clazz;
        }
    }
}
