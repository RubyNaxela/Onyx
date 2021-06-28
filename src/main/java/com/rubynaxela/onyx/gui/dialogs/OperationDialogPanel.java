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
import com.rubynaxela.onyx.data.datatypes.auxiliary.Monetary;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Contractor;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Operation;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.event.DocumentListener;

public class OperationDialogPanel extends DefaultJPanel {

    public final JLabel dateLabel, contractorLabel, descriptionLabel, amountLabel, amountPostfixLabel;
    public final JTextField dateInput, descriptionInput, amountInput;
    public final JComboBox<Contractor> contractorInput;
    public final JButton okButton;

    public OperationDialogPanel(Operation editedObject, DatabaseAccessor databaseAccessor) {

        dateLabel = new JLabel(Reference.getString("label.operation.date"));
        contractorLabel = new JLabel(Reference.getString("label.operation.contractor"));
        descriptionLabel = new JLabel(Reference.getString("label.operation.description"));
        amountLabel = new JLabel(Reference.getString("label.operation.amount"));
        amountPostfixLabel = new JLabel("PLN");
        okButton = new JButton(Reference.getString("button.ok"));

        dateInput = new JTextField();
        contractorInput = new JComboBox<>(databaseAccessor.getContractorsVector());
        descriptionInput = new JTextField(20);
        amountInput = new JTextField();

        register(dateLabel, Utils.gridElementSettings(0, 0));
        register(dateInput, Utils.gridElementSettings(0, 1));
        register(contractorLabel, Utils.gridElementSettings(1, 0));
        register(contractorInput, Utils.gridElementSettings(1, 1));
        register(descriptionLabel, Utils.gridElementSettings(2, 0));
        register(descriptionInput, Utils.gridElementSettings(2, 1));
        register(amountLabel, Utils.gridElementSettings(3, 0));
        register(amountInput, Utils.gridElementSettings(3, 1));
        register(amountPostfixLabel, Utils.gridElementSettings(3, 2));

        dateInput.setText(editedObject != null ? editedObject.getDate() : "");
        contractorInput.setSelectedItem(editedObject != null ?
                                        databaseAccessor.getObject(editedObject.getContractorUuid()) : null);
        descriptionInput.setText(editedObject != null ? editedObject.getDescription() : "");
        amountInput.setText(editedObject != null ? new Monetary(editedObject.getAmount()).toString() : "");

        final DocumentListener textFieldListener = createInputValidator();
        dateInput.getDocument().addDocumentListener(textFieldListener);
        descriptionInput.getDocument().addDocumentListener(textFieldListener);
        amountInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedObject != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
    }

    private AbstractValidInputListener createInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            protected boolean dataValid() {
                boolean amountValid = true;
                try {
                    Double.parseDouble(amountInput.getText().replace(",", "."));
                } catch (NumberFormatException e) {
                    amountValid = false;
                }
                return !dateInput.getText().equals("") &&
                       !descriptionInput.getText().equals("") &&
                       amountValid;
            }
        };
    }
}
