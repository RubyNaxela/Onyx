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

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.datatypes.OnyxObjects;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class OnyxTableModel extends DefaultTableModel {

    public static Vector<String>
            contractorsTableHeaders = new Vector<>(Arrays.asList(Reference.getString("label.contractor.name"),
                                                                 Reference.getString("label.contractor.details"))),
            invoicesTableHeaders = new Vector<>(Arrays.asList(Reference.getString("label.invoice.id"),
                                                              Reference.getString("label.invoice.date"),
                                                              Reference.getString("label.invoice.contractor"),
                                                              Reference.getString("label.invoice.total"))),
            operationsTableHeaders = new Vector<>(Arrays.asList(Reference.getString("label.operation.date"),
                                                                Reference.getString("label.operation.contractor"),
                                                                Reference.getString("label.operation.description"),
                                                                Reference.getString("label.operation.amount")));

    private final DatabaseAccessor databaseAccessor;
    private Vector<ObjectRow> dataVector;
    private OnyxObjects currentTable;

    public OnyxTableModel(DatabaseAccessor databaseAccessor) {
        super();
        this.databaseAccessor = databaseAccessor;
    }

    public void display(OnyxObjects table) {
        currentTable = table;
        if (table == OnyxObjects.CONTRACTORS)
            setDataVector(dataVector = databaseAccessor.getContractorsTableVector(), contractorsTableHeaders);
        else if (table == OnyxObjects.OPEN_INVOICES)
            setDataVector(dataVector = databaseAccessor.getOpenInvoicesTableVector(), invoicesTableHeaders);
        else if (table == OnyxObjects.CLOSED_INVOICES)
            setDataVector(dataVector = databaseAccessor.getClosedInvoicesTableVector(), invoicesTableHeaders);
        else if (table == OnyxObjects.CLAIMS)
            setDataVector(dataVector = databaseAccessor.getClaimsTableVector(), operationsTableHeaders);
        else if (table == OnyxObjects.LIABILITIES)
            setDataVector(dataVector = databaseAccessor.getLiabilitiesTableVector(), operationsTableHeaders);
        else if (table == OnyxObjects.CONTRIBUTIONS)
            setDataVector(dataVector = databaseAccessor.getContributionsTableVector(), operationsTableHeaders);
        else if (table == OnyxObjects.PAYMENTS)
            setDataVector(dataVector = databaseAccessor.getPaymentsTableVector(), operationsTableHeaders);
    }

    public void refresh() {
        display(currentTable);
    }

    public OnyxObjects getCurrentTable() {
        return currentTable;
    }

    public ObjectRow getRow(int index) {
        return dataVector.get(index);
    }
}
