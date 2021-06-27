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
import com.rubynaxela.onyx.data.datatypes.auxiliary.Monetary;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.data.datatypes.auxiliary.Quantity;
import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;
import com.rubynaxela.onyx.util.TaxRate;
import org.jetbrains.annotations.Contract;

import java.util.Vector;

@SuppressWarnings("unused")
@JsonIgnoreProperties("itemsTableVector")
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

    public Monetary calculateAmount() {
        Monetary sum = new Monetary(0);
        for (InvoiceItem item : items) sum.add(item.calculateAmount());
        return sum;
    }

    public Vector<ObjectRow> getItemsTableVector() {
        final Vector<ObjectRow> table = new Vector<>();
        for (InvoiceItem item : items) {
            final double taxRate = TaxRate.get(item.getTax()) / (1 + TaxRate.get(item.getTax()));
            final Monetary itemAmount = item.calculateAmount();
            ObjectRow itemData = new ObjectRow(item);
            itemData.add(item.getDate());
            itemData.add(item.getSource());
            itemData.add(item.getDescription());
            itemData.add(taxRate != 0 ? (new Monetary(item.getRate()).times(1 - taxRate) + " PLN") : "");
            itemData.add(item.getQuantity() != 0 ? new Quantity(item.getQuantity()).toString() : "");
            itemData.add(taxRate != 0 ? (itemAmount.times(1 - taxRate) + " PLN") : "");
            itemData.add(taxRate != 0 ? (itemAmount.times(taxRate) + " PLN") : "");
            itemData.add(item.calculateAmount() + " PLN");
            table.add(itemData);
        }
        return table;
    }
}
