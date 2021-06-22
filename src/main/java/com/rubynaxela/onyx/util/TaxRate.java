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

package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.Nullable;

public class TaxRate {

    public static double get(@Nullable String symbol) {
        if (symbol == null) return 0;
        switch (symbol) {
            case "A":
                return 0.23;
            case "B":
                return 0.18;
            case "C":
                return 0.05;
            default:
                return 0;
        }
    }
}
