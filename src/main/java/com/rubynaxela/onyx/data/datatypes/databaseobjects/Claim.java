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

import java.util.UUID;

@SuppressWarnings("unused")
public class Claim extends Transaction {

    public Claim() {
    }

    public Claim(String uuid, String date, String contractorUuid, String description, double amount) {
        this.uuid = uuid;
        this.date = date;
        this.contractorUuid = contractorUuid;
        this.description = description;
        this.amount = amount;
    }

    public Claim(OpenInvoice invoice) {
        this.uuid = UUID.randomUUID().toString();
        this.date = invoice.getDate();
        this.contractorUuid = invoice.getContractorUuid();
        this.description = "N/" + invoice.getId().replace("RK/", "");
        this.amount = invoice.calculateAmount().toDouble();
    }
}
