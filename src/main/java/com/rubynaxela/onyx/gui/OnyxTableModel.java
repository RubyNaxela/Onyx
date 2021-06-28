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
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectType;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Identifiable;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.gui.components.DefaultJButton;
import com.rubynaxela.onyx.gui.components.StaticJTable;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class OnyxTableModel extends DefaultTableModel {

    private final DatabaseAccessor databaseAccessor;
    private final StaticJTable ownerTable;
    private final DefaultJButton addButton, editButton, removeButton, documentButton;

    private Vector<ObjectRow> dataVector;
    private ObjectType currentObjectsType;
    private Identifiable currentObject;

    public OnyxTableModel(DatabaseAccessor databaseAccessor, StaticJTable ownerTable, DefaultJButton addButton,
                          DefaultJButton editButton, DefaultJButton removeButton, DefaultJButton documentButton) {
        super();
        this.databaseAccessor = databaseAccessor;
        this.ownerTable = ownerTable;
        this.addButton = addButton;
        this.editButton = editButton;
        this.removeButton = removeButton;
        this.documentButton = documentButton;
    }

    public void setupTable() {
        ownerTable.setModel(this);
        ownerTable.getSelectionModel().addListSelectionListener(e -> {
            int rowIndex = ownerTable.getSelectedRow();
            if (rowIndex >= 0 && this.getCurrentObjectsType() != null) {
                currentObject = databaseAccessor.getObject(this.getRow(rowIndex).getObjectUuid());
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
                if (getCurrentObject() instanceof Invoice) documentButton.setVisible(true);
            } else {
                currentObject = null;
                editButton.setEnabled(false);
                removeButton.setEnabled(false);
                documentButton.setVisible(false);
            }
        });
    }

    public void display(ObjectType type) {
        currentObjectsType = type;
        final Table table = databaseAccessor.getTable(type);
        setDataVector(dataVector = table.data, table.headers);
        addButton.setEnabled(true);
    }

    public void refresh() {
        display(currentObjectsType);
    }

    public ObjectType getCurrentObjectsType() {
        return currentObjectsType;
    }

    public Identifiable getCurrentObject() {
        return currentObject;
    }

    public ObjectRow getRow(int index) {
        return dataVector.get(index);
    }
}
