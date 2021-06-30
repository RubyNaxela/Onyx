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

import com.rubynaxela.onyx.data.DatabaseAccessor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InputValidator {

    public static boolean isValidNumber(String value) {
        boolean valid = true;
        try {
            Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            valid = false;
        }
        return valid;
    }

    public static boolean isValidDate(String date) {
        if (!date.matches("^\\d{4}(/\\d{2}){2}$")) return false;
        final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        df.setLenient(false);
        try {
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isValidInvoiceId(String id) {
        return id.matches("^RK/\\d{6}$");
    }

    public static boolean isExistingInvoiceId(String id, DatabaseAccessor databaseAccessor) {
        return databaseAccessor.getInvoicesVector().stream().anyMatch(i -> i.getId().equals(id));
    }
}
