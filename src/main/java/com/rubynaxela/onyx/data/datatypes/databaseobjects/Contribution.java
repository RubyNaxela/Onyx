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

@SuppressWarnings("unused")
public class Contribution extends Consideration {

    public Contribution() {
    }

    public Contribution(String uuid, String date, String contractorUuid, String description,
                        double amount, String paymentMethodUuid, String invoiceUuid) {
        this.uuid = uuid;
        this.date = date;
        this.contractorUuid = contractorUuid;
        this.description = description;
        this.amount = amount;
        this.paymentMethodUuid = paymentMethodUuid;
        this.invoiceUuid = invoiceUuid;
    }

    public Contribution(Invoice invoice) {
        super(invoice, "KP/");
    }
}
