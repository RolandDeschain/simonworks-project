/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

public abstract class JsonParsersTest {

    protected JsonReader getJJR_Reader(String aString) {

        return new JsonJavaReader(aString);
    }
    protected JsonReader getJCAR_Reader(String aString) {

        return new JsonCharArrayReader(aString);
    }
}
