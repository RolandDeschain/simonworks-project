/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion.json;

public class Tests {

    public static void main(String[] args) {
        System.out.println((int) '\r');
        System.out.println((int) '\t');
        System.out.println((int) '\n');
        System.out.println((int) ' ');

        char[] chs = new char[] {
                '/'
        , '\\'
        , ';'
        , '#'
        , '='
        , '{'
        , '}'
        , '['
        , ']'
        , Character.MIN_VALUE
        , ','
        , ' '
        , '\t'
        , '\f'
        , '\r'
        , '\n'};

        for(char c : chs) {
            System.out.println("i == " + (int) c + " ||" );
        }
    }
}
