/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

public interface Column {

    enum Operator {

        IN( " in" ),
        NIN( " not in "),
        EQ ( " = "),
        NE( " <> ");

        private String symbol;

        Operator(String symbol) {
            this.symbol = symbol;
        }

        String symbol() {
            return symbol;
        }

    }

    String name();

    String operator();

    static Column eq(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public String operator() {
                return Operator.EQ.symbol();
            }
        };
    }

    static Column ne(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public String operator() {
                return Operator.NE.symbol();
            }
        };
    }

    static Column in(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public String operator() {
                return Operator.IN.symbol();
            }
        };
    }

    static Column nin(String name) {
        return new Column() {
            @Override
            public String name() { return name; }

            @Override
            public String operator() {
                return Operator.NIN.symbol();
            }
        };
    }
}
