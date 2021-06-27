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

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public enum TaxRate {

    A(0.23), B(0.10), C(0.05), E(0), NONE(0);

    public final double rate;

    TaxRate(double rate) {
        this.rate = rate;
    }

    public static TaxRate get(@Nullable String symbol) {
        if (symbol == null) return NONE;
        try {
            return TaxRate.valueOf(symbol);
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}
