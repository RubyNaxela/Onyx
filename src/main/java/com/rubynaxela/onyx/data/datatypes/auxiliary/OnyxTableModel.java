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

package com.rubynaxela.onyx.data.datatypes.auxiliary;

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.datatypes.OnyxObjects;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class OnyxTableModel extends DefaultTableModel {

    public static Vector<String>
            contractorsTableHeaders = new Vector<>(Arrays.asList("Nazwa", "Szczegóły")),
            invoicesTableHeaders = new Vector<>(Arrays.asList("Numer", "Data", "Kontrahent", "Kwota brutto")),
            transactionsTableHeaders = new Vector<>(Arrays.asList("UUID", "Kontrahent", "Kwota"));

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
            setDataVector(dataVector = databaseAccessor.getClaimsTableVector(), transactionsTableHeaders);
        else if (table == OnyxObjects.LIABILITIES)
            setDataVector(dataVector = databaseAccessor.getLiabilitiesTableVector(), transactionsTableHeaders);
        else if (table == OnyxObjects.CONTRIBUTIONS)
            setDataVector(dataVector = databaseAccessor.getContributionsTableVector(), transactionsTableHeaders);
        else if (table == OnyxObjects.PAYMENTS)
            setDataVector(dataVector = databaseAccessor.getPaymentsTableVector(), transactionsTableHeaders);
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
