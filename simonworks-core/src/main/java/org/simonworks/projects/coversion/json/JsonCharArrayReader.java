/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

import java.io.IOException;
import java.util.function.IntPredicate;

import static org.simonworks.projects.utils.Assertions.assertNotNull;

/**
 * {@link JsonReader} implementation that reads from a char[].
 */
public class JsonCharArrayReader implements JsonReader {

    private String source;
    private char[] chars;
    private int index = 0;

    public JsonCharArrayReader(String source) {
        assertNotNull(source, "Source string cannot be null!");
        this.source = source;
        this.chars = source.toCharArray();
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public int index() {
        return index;
    }

    @Override
    public int actual() {
        return isClosed() ? 0 : chars[index];
    }

    @Override
    public boolean isClosed() {
        return index >= chars.length;
    }

    private void checkClosed() throws JsonReadException {
        if(isClosed()) {
            throw new JsonReadException("<eof> reached");
        }
    }

    @Override
    public int nextChar() throws JsonReadException {
        checkClosed();
        return chars[index++];
    }

    @Override
    public void unread(int n) {
        index -= n;
        index = index < 0 ? 0 : index;
    }

    @Override
    public void advanceUntil(IntPredicate p) throws JsonReadException {
        if( actual() != 0 ) {
            while (p.test(actual())) {
                if(isClosed()) {
                    break;
                }
                index++;
            }
        }
    }

    @Override
    public String readUntil(IntPredicate p) throws JsonReadException {
        int pos = index;
        try {
            advanceUntil(p);
        } catch(JsonReadException e) {
            e.printStackTrace();
        }
        char[] buf = new char[index - pos];
        System.arraycopy(chars, pos, buf, 0, index - pos);
        return new String(buf);
    }

    @Override
    public void expected(int c) throws JsonReadException {
        advanceUntil(i -> i != c);
        int nc = actual();
        if(c != nc) {
            throw new JsonReadException("Expected " + (char) c + ". Got " + (char) nc + " at index " + index);
        }
    }

    @Override
    public void expected(IntPredicate predicate) throws JsonReadException {
        assertNotNull(predicate, "Predicate cannot be null!");
        if(!predicate.test(actual())) {
            throw new JsonReadException("Character <" + (char) chars[index] + "> can't be validated against provided predicate");
        }
    }
}
