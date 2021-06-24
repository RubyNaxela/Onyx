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

import com.rubynaxela.onyx.data.datatypes.Invoice;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class InvoiceTableModel extends DefaultTableModel {

    public static Vector<String> invoiceItemsTableHeaders = new Vector<>(
            Arrays.asList("Data", "Żródło", "Opis", "Cena j. netto", "Ilość", "Suma netto", "VAT", "Suma brutto"));

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
