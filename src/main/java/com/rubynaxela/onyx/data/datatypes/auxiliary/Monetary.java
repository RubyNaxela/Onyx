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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Monetary {

    private long wholePart, hundredthsPart;

    public Monetary(double amount) {
        final double absAmount = Math.abs(amount);
        long _wholePart = Math.round(Math.floor(absAmount));
        long _hundredthsPart = Math.round((absAmount - _wholePart) * 100);
        if (_hundredthsPart == 100) {
            _hundredthsPart = 0;
            _wholePart++;
        }
        final int sign = amount >= 0 ? 1 : -1;
        this.wholePart = sign * _wholePart;
        this.hundredthsPart = sign * _hundredthsPart;
    }

    public Monetary(String amount) {
        this(Double.parseDouble(amount.replace(",", ".")));
    }

    @Contract(value = "_ -> new", pure = true)
    public Monetary plus(@NotNull Monetary other) {
        return new Monetary(this.toDouble() + other.toDouble());
    }

    public void add(@NotNull Monetary other) {
        final Monetary result = this.plus(other);
        this.wholePart = result.wholePart;
        this.hundredthsPart = result.hundredthsPart;
    }

    @Contract(value = "_ -> new", pure = true)
    public Monetary times(double factor) {
        return new Monetary(this.toDouble() * factor);
    }

    public double toDouble() {
        return wholePart + 0.01 * hundredthsPart;
    }

    @Override
    public String toString() {
        long absHundredths = Math.abs(this.hundredthsPart);
        return wholePart + (absHundredths < 10 ? ".0" : ".") + absHundredths;
    }
}
