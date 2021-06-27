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

package com.rubynaxela.onyx.gui;

import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class InvoiceTableModel extends DefaultTableModel {

    public static Vector<String> invoiceItemsTableHeaders = new Vector<>(
            Arrays.asList(Reference.getString("label.invoice_item.date"),
                          Reference.getString("label.invoice_item.source"),
                          Reference.getString("label.invoice_item.description"),
                          Reference.getString("label.invoice_item.net_unit_price"),
                          Reference.getString("label.invoice_item.quantity"),
                          Reference.getString("label.invoice_item.net_total"),
                          Reference.getString("label.invoice_item.tax"),
                          Reference.getString("label.invoice_item.total")));

    private final Invoice invoice;
    private Vector<ObjectRow> dataVector;

    public InvoiceTableModel(Invoice invoice) {
        super();
        this.invoice = invoice;
        refresh();
    }

    public void refresh() {
        setDataVector(dataVector = invoice.getItemsTableVector(), invoiceItemsTableHeaders);
    }

    public ObjectRow getRow(int index) {
        return dataVector.get(index);
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
