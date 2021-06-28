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

import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;

import java.util.UUID;

@SuppressWarnings("unused")
public class ClosedInvoice extends Invoice {

    protected String paymentMethodUuid, considerationUuid;

    public ClosedInvoice() {
    }

    public ClosedInvoice(ImportedInvoice data) {
        this.uuid = UUID.randomUUID().toString();
        this.id = data.getId();
        this.date = data.getDate();
        this.contractorUuid = null;
        this.paymentMethodUuid = null;
        this.considerationUuid = null;
        this.items = data.getItems();
    }

    public ClosedInvoice(String uuid, String id, String date, String contractorUuid,
                         String paymentMethodUuid, String considerationUuid, InvoiceItem[] items) {
        this.uuid = uuid;
        this.id = id;
        this.date = date;
        this.contractorUuid = contractorUuid;
        this.paymentMethodUuid = paymentMethodUuid;
        this.considerationUuid = considerationUuid;
        this.items = items;
    }

    public String getPaymentMethodUuid() {
        return paymentMethodUuid;
    }

    public void setPaymentMethodUuid(String paymentMethodUuid) {
        this.paymentMethodUuid = paymentMethodUuid;
    }

    public String getConsiderationUuid() {
        return considerationUuid;
    }

    public void setConsiderationUuid(String considerationUuid) {
        this.considerationUuid = considerationUuid;
    }
}
