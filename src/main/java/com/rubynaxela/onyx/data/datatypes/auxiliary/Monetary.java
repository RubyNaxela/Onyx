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

package com.rubynaxela.onyx.data.datatypes.auxiliary;

public class Monetary {

    private long wholePart, hundredthPart;

    public Monetary(int wholePart, int hundredthPart) {
        if (wholePart < 0 || hundredthPart < 0) throw new IllegalArgumentException("");
        this.wholePart = wholePart;
        this.hundredthPart = hundredthPart;
    }

    public void add(Monetary other) {
        this.wholePart += other.wholePart + (this.hundredthPart + other.hundredthPart) / 100;
        this.hundredthPart = (this.hundredthPart + other.hundredthPart) % 100;
    }

    @Override
    public String toString() {
        return wholePart + (hundredthPart < 10 ? ".0" : ".") + hundredthPart;
    }
}
