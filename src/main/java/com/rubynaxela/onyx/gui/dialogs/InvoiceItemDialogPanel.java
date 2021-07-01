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

import com.rubynaxela.onyx.data.datatypes.auxiliary.Monetary;
import com.rubynaxela.onyx.data.datatypes.auxiliary.Quantity;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.InvoiceItem;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.InputValidator;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;

public class InvoiceItemDialogPanel extends DefaultJPanel {

    public final JLabel dateLabel, sourceLabel, descriptionLabel, priceLabel, pricePostfixLabel, quantityLabel, taxLabel;
    public final JTextField dateInput, sourceInput, descriptionInput, priceInput, quantityInput, taxInput;
    public final JButton okButton;

    public InvoiceItemDialogPanel(InvoiceItem editedObject) {

        dateLabel = new JLabel(Reference.getString("label.invoice_item.date"));
        sourceLabel = new JLabel(Reference.getString("label.invoice_item.source"));
        descriptionLabel = new JLabel(Reference.getString("label.invoice_item.description"));
        priceLabel = new JLabel(Reference.getString("label.invoice_item.price"));
        pricePostfixLabel = new JLabel("PLN");
        quantityLabel = new JLabel(Reference.getString("label.invoice_item.quantity"));
        taxLabel = new JLabel(Reference.getString("label.invoice_item.tax"));
        okButton = new JButton(Reference.getString("button.ok"));

        dateInput = new JTextField();
        sourceInput = new JTextField(10);
        descriptionInput = new JTextField(30);
        priceInput = new JTextField();
        quantityInput = new JTextField();
        taxInput = new JTextField();

        register(dateLabel, Utils.gridPosition(0, 0));
        register(dateInput, Utils.gridPosition(0, 1));
        register(sourceLabel, Utils.gridPosition(1, 0));
        register(sourceInput, Utils.gridPosition(1, 1));
        register(descriptionLabel, Utils.gridPosition(2, 0));
        register(descriptionInput, Utils.gridPosition(2, 1, 2, 1));
        register(priceLabel, Utils.gridPosition(3, 0));
        register(priceInput, Utils.gridPosition(3, 1));
        register(pricePostfixLabel, Utils.gridPosition(3, 2));
        register(quantityLabel, Utils.gridPosition(4, 0));
        register(quantityInput, Utils.gridPosition(4, 1));
        register(taxLabel, Utils.gridPosition(5, 0));
        register(taxInput, Utils.gridPosition(5, 1));

        dateInput.setText(editedObject != null ? editedObject.getDate() : "");
        sourceInput.setText(editedObject != null ? editedObject.getSource() : "");
        descriptionInput.setText(editedObject != null ? editedObject.getDescription() : "");
        priceInput.setText(editedObject != null ? new Monetary(editedObject.getRate()).toString() : "");
        quantityInput.setText(editedObject != null ? new Quantity(editedObject.getQuantity()).toString() : "");
        taxInput.setText(editedObject != null ? editedObject.getTax() : "");

        final AbstractValidInputListener textFieldListener = createTextInputValidator();
        dateInput.getDocument().addDocumentListener(textFieldListener);
        sourceInput.getDocument().addDocumentListener(textFieldListener);
        descriptionInput.getDocument().addDocumentListener(textFieldListener);
        priceInput.getDocument().addDocumentListener(textFieldListener);
        quantityInput.getDocument().addDocumentListener(textFieldListener);
        taxInput.getDocument().addDocumentListener(textFieldListener);

        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
        okButton.setEnabled(textFieldListener.dataValid());
    }

    private AbstractValidInputListener createTextInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            public boolean dataValid() {

                final boolean dateValid = InputValidator.isValidDate(dateInput.getText()),
                        descriptionValid = !descriptionInput.getText().equals(""),
                        priceValid = InputValidator.isValidNumber(priceInput.getText()),
                        quantityValid = InputValidator.isValidNumber(quantityInput.getText()),
                        taxValid = InputValidator.isValidTaxRate(taxInput.getText());

                if (!dateValid && !dateInput.getText().equals(""))
                    displayError(dateInput, Reference.getString("input.invalid.date"));
                else cancelError(dateInput);
                if (!priceValid && !priceInput.getText().equals(""))
                    displayError(priceInput, Reference.getString("input.invalid.number"));
                else cancelError(priceInput);
                if (!quantityValid && !quantityInput.getText().equals(""))
                    displayError(quantityInput, Reference.getString("input.invalid.number"));
                else cancelError(quantityInput);
                if (!taxValid && !taxInput.getText().equals(""))
                    displayError(taxInput, Reference.getString("input.invalid.tax_rate"));
                else cancelError(taxInput);

                return dateValid && descriptionValid && priceValid &&
                       (quantityValid || quantityInput.getText().equals("")) && (taxValid || taxInput.getText().equals(""));
            }
        };
    }
}