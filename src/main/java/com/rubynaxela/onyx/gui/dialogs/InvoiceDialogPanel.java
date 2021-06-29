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

package com.rubynaxela.onyx.gui.dialogs;

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.ClosedInvoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Contractor;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.InvoiceItem;
import com.rubynaxela.onyx.gui.InvoiceTableModel;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.gui.components.DefaultJScrollPane;
import com.rubynaxela.onyx.gui.components.StaticJTable;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public final class InvoiceDialogPanel extends DefaultJPanel {

    public final JLabel idLabel, dateLabel, contractorLabel, itemsLabel, clearedLabel;
    public final JTextField idInput, dateInput;
    public final JComboBox<Contractor> contractorInput;
    public final JCheckBox clearedCheckBox;
    public final StaticJTable itemsTable;
    public final InvoiceTableModel itemsTableModel;
    public final JButton okButton;

    public InvoiceDialogPanel(Invoice editedObject, DatabaseAccessor databaseAccessor) {

        idLabel = new JLabel(Reference.getString("label.invoice.id"));
        dateLabel = new JLabel(Reference.getString("label.invoice.date"));
        contractorLabel = new JLabel(Reference.getString("label.invoice.contractor"));
        itemsLabel = new JLabel(Reference.getString("label.invoice.items"));
        clearedLabel = new JLabel(Reference.getString("label.invoice.cleared"));
        idInput = new JTextField();
        dateInput = new JTextField();
        contractorInput = new JComboBox<>(databaseAccessor.getContractorsVector());
        clearedCheckBox = new JCheckBox();
        itemsTable = new StaticJTable();
        itemsTableModel = new InvoiceTableModel(editedObject);
        itemsTableModel.addTableModelListener(e -> itemsTable.resizeColumnWidth(15, 300));
        okButton = new JButton(Reference.getString("button.ok"));

        register(idLabel, Utils.gridElementSettings(0, 0));
        register(idInput, Utils.gridElementSettings(0, 1));
        register(dateLabel, Utils.gridElementSettings(1, 0));
        register(dateInput, Utils.gridElementSettings(1, 1));
        register(contractorLabel, Utils.gridElementSettings(0, 2));
        register(contractorInput, Utils.gridElementSettings(0, 3, 3, 1));
        register(clearedLabel, Utils.gridElementSettings(1, 2));
        register(clearedCheckBox, Utils.gridElementSettings(1, 3));
        register(itemsLabel, Utils.gridElementSettings(2, 0, 6, 1));
        register(new DefaultJScrollPane(itemsTable, 900, 350),
                 Utils.gridElementSettings(3, 0, 7, 1));

        idInput.setText(editedObject != null ? editedObject.getId() : "");
        dateInput.setText(editedObject != null ? editedObject.getDate() : "");
        contractorInput.setSelectedItem(editedObject != null ?
                                        databaseAccessor.getObject(editedObject.getContractorUuid()) : null);
        clearedCheckBox.setSelected(editedObject instanceof ClosedInvoice);
        itemsTable.setModel(itemsTableModel);
        itemsTable.resizeColumnWidth(15, 300);

        final DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        itemsTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        itemsTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        itemsTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        itemsTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        itemsTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);

        final AbstractValidInputListener textFieldListener = createInputValidator();
        idInput.getDocument().addDocumentListener(textFieldListener);
        dateInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedObject != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
        okButton.setEnabled(textFieldListener.dataValid());
    }

    private AbstractValidInputListener createInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            public boolean dataValid() {
                return idInput.getText().matches("^RK/\\d{6}$") &&
                       !dateInput.getText().equals("");
            }
        };
    }

    public InvoiceItem[] getInvoiceItems() {
        return itemsTableModel.getInvoice().getItems();
    }
}
