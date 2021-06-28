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

    public Monetary(long wholePart, long hundredthsPart) {
        if (wholePart < 0 != hundredthsPart < 0 && wholePart != 0 && hundredthsPart != 0)
            throw new IllegalArgumentException("The whole part and the hundredths part cannot have different signs");
        if (hundredthsPart <= -100 || hundredthsPart >= 100)
            throw new IllegalArgumentException("The hundredths part must be greater than -100 and less than 100");
        this.wholePart = wholePart;
        this.hundredthsPart = hundredthsPart;
    }

    public Monetary(long wholePart, long hundredthsPart, boolean negative) {
        this(negative ? -wholePart : wholePart, negative ? -hundredthsPart : hundredthsPart);
    }

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

    public void add(@NotNull Monetary other) {
        this.wholePart += other.wholePart + (this.hundredthsPart + other.hundredthsPart) / 100;
        this.hundredthsPart = (this.hundredthsPart + other.hundredthsPart) % 100;
    }

    @Contract(value = "_ -> new", pure = true)
    public Monetary plus(@NotNull Monetary other) {
        final Monetary sum = new Monetary(wholePart, hundredthsPart);
        sum.add(other);
        return sum;
    }

    public void multiply(double factor) {
        final Monetary result = new Monetary(factor * (wholePart + hundredthsPart / 100.0));
        this.wholePart = result.wholePart;
        this.hundredthsPart = result.hundredthsPart;
    }

    @Contract(value = "_ -> new", pure = true)
    public Monetary times(double factor) {
        final Monetary product = new Monetary(wholePart, hundredthsPart);
        product.multiply(factor);
        return product;
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
