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

package com.rubynaxela.onyx.data.datatypes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@SuppressWarnings("unused")
@JsonIgnoreProperties("uuid")
public class InvoiceItem implements Identifiable {

    private String uuid, date, source, description, tax;
    private double rate, quantity;

    public String getDate() {
        return date;
    }

    public String getSource() {
        return source;
    }

    public String getDescription() {
        return description;
    }

    public String getTax() {
        return tax;
    }

    public double getRate() {
        return rate;
    }

    public double getQuantity() {
        return quantity;
    }

    @Override
    public String getUuid() {
        if (uuid == null) uuid = UUID.randomUUID().toString();
        return uuid;
    }
}
