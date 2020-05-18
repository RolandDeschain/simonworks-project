/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 *  SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

package org.simonworks.projects.coversion;

import java.util.Arrays;
import java.util.StringJoiner;

public class SimpleBean {

    private String name;
    private String number;
    private Double amount;
    private boolean boolField;
    private short thisIsShort;
    private SimpleBean[] beans;
    private SimpleBean child;

    public SimpleBean() {
    }

    public SimpleBean(String name) {
        this.name = name;
    }

    public SimpleBean(String name, String number) {
        this(name);
        this.number = number;
    }

    public SimpleBean(String name, String number, Double amount) {
        this(name, number);
        this.amount = amount;
    }

    public SimpleBean(String name, String number, Double amount, boolean boolField) {
        this(name, number, amount);
        this.boolField = boolField;
    }
    public SimpleBean(String name, String number, Double amount, boolean boolField, short thisIsShort) {
        this(name, number, amount, boolField);
        this.thisIsShort = thisIsShort;
    }

    public SimpleBean(String name, String number, Double amount, boolean boolField, short thisIsShort, SimpleBean child) {
        this(name, number, amount, boolField, thisIsShort);
        this.child = child;
    }



    public SimpleBean(String name, String number, Double amount, boolean boolField, short thisIsShort, SimpleBean[] beans) {
        this(name, number, amount, boolField, thisIsShort);
        this.beans = beans;
    }

    public boolean isBoolField() {
        return boolField;
    }

    public void setBoolField(boolean boolField) {
        this.boolField = boolField;
    }

    public SimpleBean[] getBeans() {
        return beans;
    }

    public void setBeans(SimpleBean[] beans) {
        this.beans = beans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public short getThisIsShort() {
        return thisIsShort;
    }

    public void setThisIsShort(short thisIsShort) {
        this.thisIsShort = thisIsShort;
    }

    public SimpleBean getChild() {
        return child;
    }

    public void setChild(SimpleBean child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SimpleBean.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("number='" + number + "'")
                .add("amount=" + amount)
                .add("boolField=" + boolField)
                .add("thisIsShort=" + thisIsShort)
                .add("beans=" + Arrays.toString(beans))
                .add("child=" + child)
                .toString();
    }
}
