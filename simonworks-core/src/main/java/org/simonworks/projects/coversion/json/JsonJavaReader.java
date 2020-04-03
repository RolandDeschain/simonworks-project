/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

/**
 * {@link JsonReader} implementation that delegates the json reading capability to an underlying {@link Reader}.
 * The default {@link Reader} used is {@link StringReader} or this {@link JsonJavaReader} can be created using
 * the {@link JsonJavaReader#JsonJavaReader(Reader)} constructor.
 */
public class JsonJavaReader implements JsonReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonJavaReader.class);

    private int lastRead;
    private int index = 0;
    private Reader reader;
    private String source;

    public JsonJavaReader(String source) {
        this(new StringReader(source));
        this.source = source;
    }

    public JsonJavaReader(Reader reader) {
        this.reader = reader;
    }

    /**
     * This method implementation has different behaviours depending on how this {@link JsonJavaReader} has been created:
     *
     * @return
     *  <p>
     *      Case a) If the {@link JsonJavaReader#JsonJavaReader(String)} constructor has been used,
     *      than the original String is returned
     *  </p>
     *
     *  <p>
     *      Case b) If the {@link JsonJavaReader#JsonJavaReader(Reader)} constructor has been used,
     *      than the characters, not read at the invoke time, will be returned.
     *  </p>
     * @throws IOException
     *  Only in Case b), if any error occurs.
     */
    @Override
    public String getSource() throws IOException {
        // if JsonJavaReader(String) constructor as been used, lets directly return the String passed in input.
        if(this.source != null) {
            return this.source;
        }
        try {
            return readUntil( JsonReader.untilEnd );
        } catch (JsonReadException e) {
            throw new IOException(e);
        }
    }

    /**
     * Reading index.
     *
     * @return The reading index.
     */
    @Override
    public int index() {
        return index;
    }

    /**
     * Last char read
     *
     * @return Last char read
     */
    @Override
    public int actual() {
        return lastRead;
    }

    /**
     * Indicates if this JsonReader is closed and cannot read more
     *
     * @return true if this JsonReader cannot read more, false otherwise
     */
    @Override
    public boolean isClosed() {
        try {
            return !reader.ready();
        } catch (IOException e) {
            LOGGER.error("Error checking if this reader isClosed.", e);
            return true;
        }
    }

    /**
     * Reads next character from source String.
     *
     * @return Next character in source String
     * @throws JsonReadException If cannot read over from source String
     */
    @Override
    public int nextChar() throws JsonReadException {
        try {
            lastRead = reader.read();
            index++;
        } catch (IOException e) {
            throw new JsonReadException(e);
        }
        return lastRead;
    }

    /**
     * Unread n last characters.
     *
     * @param n
     */
    @Override
    public void unread(int n) {
        try {
            reader.reset();
        } catch (IOException e) {
            LOGGER.error("Cannot reset reader");
        }
        index -= n + 1;
    }

    /**
     * Advances reading until provided {@link Predicate} tests
     *
     * @param p p Continue reading condition.
     * @throws JsonReadException If cannot read over from source String
     */
    @Override
    public void advanceUntil(IntPredicate p) throws JsonReadException {
        while( p.test(lastRead) ) {
            nextChar();
        }
    }

    @Override
    public void skipWhiteSpaces() {
        try {
            advanceUntil(whiteSpaces.or(i -> i == 0));
        } catch (JsonReadException e) {
            /**
             * Nothing to do
             */
        }
    }

    /**
     * Advances reading until provided {@link Predicate} tests while collecting read chars.
     *
     * @param p
     * @return Read chars while advancing
     * @throws JsonReadException If cannot read over from source String
     */
    @Override
    public String readUntil(IntPredicate p) throws JsonReadException {
        StringBuilder sb = new StringBuilder();
        while( lastRead != -1 && p.test(lastRead) ) {
            sb.append((char)lastRead);
            nextChar();
        }
        return sb.toString();
    }

    /**
     * Verifies that the next character is equal to the one provided.
     *
     * @param c The expected character.
     * @throws JsonReadException If next character is not equal to the one provided.
     */
    @Override
    public void expected(int c) throws JsonReadException {
        advanceUntil(i -> i != -1 && i != c);
        int nc = actual();
        if(c != nc) {
            throw new JsonReadException("Expected " + (char) c + ". Got " + (char) nc + " at index " + index);
        }
        try {
            reader.mark(index);
        } catch (IOException e) {
            throw new JsonReadException(e);
        }
    }

    /**
     * Verifies that next character is validated against provided {@link IntPredicate}
     *
     * @param predicate The predicate used to validate actual next character
     * @throws JsonReadException If next character is not equal to the one provided.
     */
    @Override
    public void expected(IntPredicate predicate) throws JsonReadException {
        assertNotNull(predicate, "Predicate cannot be null!");
        if(!predicate.test(actual())) {
            throw new JsonReadException("Character <" + (char) actual() + "> can't be validated against provided predicate");
        }
        try {
            reader.mark(index);
        } catch (IOException e) {
            throw new JsonReadException(e);
        }
    }
}
