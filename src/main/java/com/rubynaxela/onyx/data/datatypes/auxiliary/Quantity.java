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

public class Quantity {

    private final long wholePart, thousandthsPart;

    public Quantity(double quantity) {
        final double absQuantity = Math.abs(quantity);
        long _wholePart = Math.round(Math.floor(absQuantity));
        long _thousandthsPart = Math.round((absQuantity - _wholePart) * 1000);
        if (_thousandthsPart == 1000) {
            _thousandthsPart = 0;
            _wholePart++;
        }
        final int sign = quantity >= 0 ? 1 : -1;
        this.wholePart = sign * _wholePart;
        this.thousandthsPart = sign * _thousandthsPart;
    }

    public Quantity(String amount) {
        this(Double.parseDouble(amount.replace(",", ".")));
    }

    public double toDouble() {
        return wholePart + 0.001 * thousandthsPart;
    }

    @Override
    public String toString() {
        long absThousandths = Math.abs(this.thousandthsPart);
        String string = "" + wholePart;
        if (thousandthsPart != 0) {
            string += ".";
            if (thousandthsPart < 100) string += 0;
            if (thousandthsPart < 10) string += 0;
            string += absThousandths;
        }
        return string;
    }
}
