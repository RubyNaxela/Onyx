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
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Identifiable;
import com.rubynaxela.onyx.data.datatypes.auxiliary.OnyxObjectsGroup;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.gui.components.DefaultJButton;
import com.rubynaxela.onyx.gui.components.StaticJTable;
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
    private final StaticJTable ownerTable;
    private final DefaultJButton addButton, editButton, removeButton;

    public Identifiable currentObject;
    private Vector<ObjectRow> dataVector;
    private OnyxObjectsGroup currentObjects;

    public OnyxTableModel(DatabaseAccessor databaseAccessor, StaticJTable ownerTable,
                          DefaultJButton addButton, DefaultJButton editButton, DefaultJButton removeButton) {
        super();
        this.databaseAccessor = databaseAccessor;
        this.ownerTable = ownerTable;
        this.addButton = addButton;
        this.editButton = editButton;
        this.removeButton = removeButton;

        ownerTable.setModel(this);
        ownerTable.getSelectionModel().addListSelectionListener(e -> {
            int rowIndex = ownerTable.getSelectedRow();
            if (rowIndex >= 0 && this.getCurrentObjects() != null) {
                currentObject = databaseAccessor.getObject(this.getRow(rowIndex).getObjectUuid());
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            } else {
                currentObject = null;
                editButton.setEnabled(false);
                removeButton.setEnabled(false);
            }
        });
    }

    public Identifiable getCurrentObject() {
        return currentObject;
    }

    public void display(OnyxObjectsGroup table) {
        currentObjects = table;
        if (table == OnyxObjectsGroup.CONTRACTORS)
            setDataVector(dataVector = databaseAccessor.getContractorsTableVector(), contractorsTableHeaders);
        else if (table == OnyxObjectsGroup.OPEN_INVOICES)
            setDataVector(dataVector = databaseAccessor.getOpenInvoicesTableVector(), invoicesTableHeaders);
        else if (table == OnyxObjectsGroup.CLOSED_INVOICES)
            setDataVector(dataVector = databaseAccessor.getClosedInvoicesTableVector(), invoicesTableHeaders);
        else if (table == OnyxObjectsGroup.CLAIMS)
            setDataVector(dataVector = databaseAccessor.getClaimsTableVector(), operationsTableHeaders);
        else if (table == OnyxObjectsGroup.LIABILITIES)
            setDataVector(dataVector = databaseAccessor.getLiabilitiesTableVector(), operationsTableHeaders);
        else if (table == OnyxObjectsGroup.CONTRIBUTIONS)
            setDataVector(dataVector = databaseAccessor.getContributionsTableVector(), operationsTableHeaders);
        else if (table == OnyxObjectsGroup.PAYMENTS)
            setDataVector(dataVector = databaseAccessor.getPaymentsTableVector(), operationsTableHeaders);

        addButton.setEnabled(true);
    }

    public void refresh() {
        display(currentObjects);
    }

    public OnyxObjectsGroup getCurrentObjects() {
        return currentObjects;
    }

    public ObjectRow getRow(int index) {
        return dataVector.get(index);
    }
}
