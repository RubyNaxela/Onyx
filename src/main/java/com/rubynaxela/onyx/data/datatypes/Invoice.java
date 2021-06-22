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
import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public abstract class Invoice implements Identifiable {

    protected String uuid, id, date, contractorUuid;
    protected InvoiceItem[] items;

    protected Invoice() {
    }

    @Contract(value = "_ -> new", pure = true)
    public static Invoice imported(ImportedInvoice data) {
        return data.isOpen() ? new OpenInvoice(data) : new ClosedInvoice(data);
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getContractorUuid() {
        return contractorUuid;
    }

    public InvoiceItem[] getItems() {
        return items;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    public int calculateAmount() {
        int sum = 0;
        for (InvoiceItem item : items) sum += Math.round(100 * item.getQuantity() * item.getRate());
        return sum;
    }
}
