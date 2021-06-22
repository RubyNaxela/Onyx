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
        wholePart = (amount < 0 ? -1 : 1) * Math.round(Math.floor(absAmount));
        hundredthsPart = (amount < 0 ? -1 : 1) * Math.round((absAmount - Math.abs(wholePart)) * 100);
    }

    public void add(@NotNull Monetary other) {
        this.wholePart += other.wholePart + (this.hundredthsPart + other.hundredthsPart) / 100;
        this.hundredthsPart = (this.hundredthsPart + other.hundredthsPart) % 100;
    }

    @Contract(value = "_ -> new", pure = true)
    public Monetary plus(@NotNull Monetary other) {
        Monetary sum = new Monetary(wholePart, hundredthsPart);
        sum.add(other);
        return sum;
    }

    public void multiply(double factor) {
        wholePart = Math.round(wholePart * factor) + Math.round(hundredthsPart * factor) / 100;
        hundredthsPart = Math.round(hundredthsPart * factor) % 100;
    }

    @Contract(value = "_ -> new", pure = true)
    public Monetary times(double factor) {
        Monetary product = new Monetary(wholePart, hundredthsPart);
        product.multiply(factor);
        return product;
    }

    @Override
    public String toString() {
        long absHundredths = Math.abs(this.hundredthsPart);
        return wholePart + (absHundredths < 10 ? ".0" : ".") + absHundredths;
    }
}
