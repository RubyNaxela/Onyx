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

package com.rubynaxela.onyx.data.datatypes.databaseobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties("reference")
public abstract class Operation implements Identifiable, Referring {

    protected String uuid, date, contractorUuid, description, invoiceUuid;
    protected double amount;

    public Operation() {
    }

    public Operation(Invoice invoice, String idPrefix) {
        this.uuid = UUID.randomUUID().toString();
        this.date = invoice.getDate();
        this.contractorUuid = invoice.getContractorUuid();
        this.description = idPrefix + invoice.getId().replace("RK/", "");
        this.amount = invoice.calculateAmount().toDouble();
        this.invoiceUuid = invoice.getUuid();
    }

    public String getDate() {
        return date;
    }

    public String getContractorUuid() {
        return contractorUuid;
    }

    public String getDescription() {
        return description;
    }

    public String getInvoiceUuid() {
        return invoiceUuid;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public String getReference() {
        return invoiceUuid;
    }

    @Override
    public void removeReference() {
        invoiceUuid = null;
    }
}
