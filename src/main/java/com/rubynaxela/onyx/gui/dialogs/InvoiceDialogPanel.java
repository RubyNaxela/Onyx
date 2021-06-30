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
import com.rubynaxela.onyx.util.InputValidator;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;

public final class InvoiceDialogPanel extends DefaultJPanel {

    public final JLabel idLabel, dateLabel, contractorLabel, itemsLabel, clearedLabel;
    public final JTextField idInput, dateInput;
    public final JComboBox<Contractor> contractorInput;
    public final JCheckBox clearedCheckBox;
    public final StaticJTable itemsTable;
    public final InvoiceTableModel itemsTableModel;
    public final JButton okButton;

    public InvoiceDialogPanel(Invoice editedObject, DatabaseAccessor databaseAccessor) {

        String invoiceId = editedObject != null ? editedObject.getId() : "";
        if (!invoiceId.startsWith("RK/")) invoiceId = "RK/" + invoiceId;

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

        register(idLabel, Utils.gridPosition(0, 0));
        register(idInput, Utils.gridPosition(0, 1));
        register(dateLabel, Utils.gridPosition(1, 0));
        register(dateInput, Utils.gridPosition(1, 1));
        register(contractorLabel, Utils.gridPosition(0, 2));
        register(contractorInput, Utils.gridPosition(0, 3, 3, 1));
        register(clearedLabel, Utils.gridPosition(1, 2));
        register(clearedCheckBox, Utils.gridPosition(1, 3));
        register(itemsLabel, Utils.gridPosition(2, 0, 6, 1));
        register(new DefaultJScrollPane(itemsTable, 900, 350),
                 Utils.gridPosition(3, 0, 7, 1));

        idInput.setText(invoiceId);
        dateInput.setText(editedObject != null ? editedObject.getDate() : "");
        contractorInput.setSelectedItem(editedObject != null ?
                                        databaseAccessor.getObject(editedObject.getContractorUuid()) : null);
        clearedCheckBox.setSelected(editedObject instanceof ClosedInvoice);
        itemsTable.setModel(itemsTableModel);
        itemsTable.resizeColumnWidth(15, 300);

        itemsTable.alignColumn(3, JLabel.RIGHT);
        itemsTable.alignColumn(4, JLabel.RIGHT);
        itemsTable.alignColumn(5, JLabel.RIGHT);
        itemsTable.alignColumn(6, JLabel.RIGHT);
        itemsTable.alignColumn(7, JLabel.RIGHT);

        final AbstractValidInputListener textFieldListener = createTextInputValidator();
        idInput.getDocument().addDocumentListener(textFieldListener);
        dateInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedObject != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
        okButton.setEnabled(textFieldListener.dataValid());
    }

    public InvoiceItem[] getInvoiceItems() {
        return itemsTableModel.getInvoice().getItems();
    }

    private AbstractValidInputListener createTextInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            public boolean dataValid() {

                final boolean dateValid = InputValidator.isValidDate(dateInput.getText()),
                        idValid = InputValidator.isValidInvoiceId(idInput.getText());

                if (!dateValid && !dateInput.getText().equals(""))
                    displayError(dateInput, Reference.getString("input.invalid.date"));
                else cancelError(dateInput);
                if (!idValid && !idInput.getText().equals(""))
                    displayError(idInput, Reference.getString("input.invalid.invoice_id"));
                else cancelError(idInput);

                return dateValid && idValid;
            }
        };
    }
}
