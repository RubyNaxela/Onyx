/*
 * Copyright (c) 2021 RubyNaxela
 * All Rights Reserved
 *
 * This file is part of the Onyx project.
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 * Proprietary and confidential.
 *
 * Written by Jacek Pawelski <rubynaxela@gmail.com>
 */

package com.rubynaxela.onyx.gui;

import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;

import java.util.Vector;

public class Table {

    public final Vector<String> headers;
    public final Vector<ObjectRow> data;

    public Table(Vector<String> headers, Vector<ObjectRow> data) {
        this.headers = headers;
        this.data = data;
    }
}
