/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

public class Parsers {

    private static final JsonParser<String> STRING_JSON_PARSER = new JsonStringParser();
    private static final JsonParser<Boolean> BOOLEAN_JSON_PARSER = new JsonBooleanParser();
    private static final JsonParser<Double> DOUBLE_JSON_PARSER = new NumberParsers.JsonDoubleParser();
    private static final JsonParser<Integer> INTEGER_JSON_PARSER = new NumberParsers.JsonIntegerParser();
    private static final JsonParser<Long> LONG_JSON_PARSER = new NumberParsers.JsonLongParser();
    private static final JsonParser<Float> FLOAT_JSON_PARSER = new NumberParsers.JsonFloatParser();
    private static final JsonParser<Short> SHORT_JSON_PARSER = new NumberParsers.JsonShortParser();
    private static final JsonParser<Byte> BYTE_JSON_PARSER = new NumberParsers.JsonByteParser();

    private Parsers() {}

    public static <O> O parse(JsonReader reader, Class<O> clazz) throws JsonParseException {
        Object result = null;
        switch(clazz.getName()) {
            case "java.lang.String" :
                result = STRING_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Boolean" :
            case "boolean" :
                result = BOOLEAN_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Double" :
            case "double" :
                result = DOUBLE_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Integer" :
            case "integer" :
                result = INTEGER_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Long" :
            case "long" :
                result = LONG_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Float" :
            case "float" :
                result = FLOAT_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Short" :
            case "short" :
                result = SHORT_JSON_PARSER.parse(reader);
                break;
            case "java.lang.Byte" :
            case "byte" :
                result = BYTE_JSON_PARSER.parse(reader);
                break;
            default: return null;
        }
        return (O) result;
    }
}
