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

    public ClosedInvoice() {
    }

    public ClosedInvoice(ImportedInvoice data) {
        this.uuid = UUID.randomUUID().toString();
        this.id = data.getId();
        this.date = data.getDate();
        this.contractorUuid = null;
        this.items = data.getItems();
    }

    public ClosedInvoice(String uuid, String id, String date, String contractorUuid, InvoiceItem[] items) {
        this.uuid = uuid;
        this.id = id;
        this.date = date;
        this.contractorUuid = contractorUuid;
        this.items = items;
    }
}
