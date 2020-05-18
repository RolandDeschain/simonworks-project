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

import org.simonworks.projects.domain.Source;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Reader object that provides functionality to read from Json strings.
 *
 * @implSpec  http://www.asciitable.com
 */
public interface JsonReader extends Source<String> {

    IntPredicate untilEnd = i -> i!=-1;

    IntPredicate whiteSpaces = Character::isWhitespace;

    IntPredicate valueSeparators = whiteSpaces.or(i -> i == 44);

    IntPredicate stringStarters = Character::isJavaIdentifierStart;

    IntPredicate literals = Character::isJavaIdentifierPart;

    IntPredicate notLiterals = literals.negate();

    static String readWithoutQuote(JsonReader reader) throws JsonReadException {
        return reader.readUntil(literals);
    }

    static String readWithQuote(JsonReader reader, int quote) throws JsonReadException {
        reader.expected(quote);
        reader.nextChar();
        String key = reader.readUntil(literals);
        reader.expected(quote);
        reader.nextChar();
        return key;
    }

    /**
     * Reading index.
     *
     * @return
     *  The reading index.
     */
    int index();

    /**
     * Last char read
     *
     * @return
     *  Last char read
     */
    int actual();

    /**
     * Indicates if this JsonReader is closed and cannot read more
     *
     * @return
     *  true if this JsonReader cannot read more, false otherwise
     */
    boolean isClosed();

    /**
     * Reads next character from source String.
     * @return
     *  Next character in source String
     * @throws JsonReadException
     *  If cannot read over from source String
     */
    int nextChar() throws JsonReadException;

    /**
     * Unread n last characters.
     */
    void unread(int n);

    /**
     * Unread last character.
     */
    default void unread() {
        unread(1);
    }

    default void skipWhiteSpaces() {
        try {
            advanceUntil(whiteSpaces);
        } catch (JsonReadException e) {
            /**
             * Do nothing
             */
        }
    }

    /**
     * Advances reading until provided {@link Predicate} tests
     *
     * @param p
     *  p Continue reading condition.
     * @throws JsonReadException
     *  If cannot read over from source String
     */
    void advanceUntil(IntPredicate p) throws JsonReadException;

    /**
     * Advances reading until provided {@link Predicate} tests while collecting read chars.
     *
     * @param
     *  p Continue reading condition.
     * @return
     *  Read chars while advancing
     * @throws JsonReadException
     *  If cannot read over from source String
     */
    String readUntil(IntPredicate p) throws JsonReadException;

    /**
     * Verifies that actual character is equal to the one provided.
     *
     * @param c
     *  The expected character.
     * @throws JsonReadException
     *  If next character is not equal to the one provided.
     */
    default void expected(char c) throws JsonReadException {
        expected((int) c);
    }

    /**
     * Verifies that the next character is equal to the one provided.
     *
     * @param c
     *  The expected character.
     * @throws JsonReadException
     *  If next character is not equal to the one provided.
     */
    void expected(int c) throws JsonReadException;

    /**
     * Verifies that next character is validated against provided {@link IntPredicate}
     *
     * @param predicate
     *  The predicate used to validate actual next character
     * @throws JsonReadException
     *  If next character is not equal to the one provided.
     */
    void expected(IntPredicate predicate) throws JsonReadException;
}
