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

import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;

import java.util.UUID;

public class OpenInvoice extends Invoice {

    public OpenInvoice() {
    }

    public OpenInvoice(ImportedInvoice data) {
        this.uuid = UUID.randomUUID().toString();
        this.id = data.getId();
        this.date = data.getDate();
        this.contractorUuid = "?";
        this.items = data.getItems();
    }

    public OpenInvoice(String uuid, String id, String date, String contractorUuid, InvoiceItem[] items) {
        this.uuid = uuid;
        this.id = id;
        this.date = date;
        this.contractorUuid = contractorUuid;
        this.items = items;
    }
}
