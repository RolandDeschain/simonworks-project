/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

interface Column {

    enum Operator {

        IN( "in" ),
        NIN( " not in "),
        EQ ( " = "),
        NE( " <> ");

        private String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        String operator() {
            return operator;
        }

    }

    String name();

    Operator operator();

    static Column eq(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public Operator operator() {
                return Operator.EQ;
            }
        };
    }

    static Column ne(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public Operator operator() {
                return Operator.NE;
            }
        };
    }

    static Column in(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public Operator operator() {
                return Operator.IN;
            }
        };
    }

    static Column nin(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public Operator operator() {
                return Operator.NIN;
            }
        };
    }
}
