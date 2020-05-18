/*
 * Copyright (c) 2019, SimonWorks and/or its affiliates. All rights reserved.
 * SIMONWORKS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.simonworks.projects.dao.query;

abstract class AbstractStatement implements QueryComplete {

    private String table;
    private Column[] columns;
    private Column[] whereConditions;

    Column[] getColumns() {
        return columns;
    }

    void setColumns(Column ... columns) {
        this.columns = columns;
    }

    Column[] getWhereConditions() {
        return whereConditions;
    }

    void setWhereConditions(Column ... whereConditions) {
        if(whereConditions != null && whereConditions.length != 0) {
            this.whereConditions = whereConditions;
        }
    }

    void setTable(String table) {
        this.table = table;
    }

    String getTable() {
        return table;
    }
}
