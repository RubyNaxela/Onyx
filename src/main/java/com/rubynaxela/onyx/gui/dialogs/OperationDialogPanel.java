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
import com.rubynaxela.onyx.data.datatypes.auxiliary.PaymentMethod;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Consideration;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Contractor;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Operation;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.InputValidator;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;

public final class OperationDialogPanel extends DefaultJPanel {

    public final JLabel dateLabel, contractorLabel, descriptionLabel,
            amountLabel, amountPostfixLabel, paymentMethodLabel, invoiceIdLabel;
    public final JTextField dateInput, descriptionInput, amountInput, invoiceIdInput;
    public final JComboBox<Contractor> contractorInput;
    public final JComboBox<PaymentMethod> paymentMethodInput;
    public final JButton okButton;

    private final DatabaseAccessor databaseAccessor;

    public OperationDialogPanel(Operation editedObject, DatabaseAccessor databaseAccessor) {

        this.databaseAccessor = databaseAccessor;

        dateLabel = new JLabel(Reference.getString("label.operation.date"));
        contractorLabel = new JLabel(Reference.getString("label.operation.contractor"));
        descriptionLabel = new JLabel(Reference.getString("label.operation.description"));
        amountLabel = new JLabel(Reference.getString("label.operation.amount"));
        amountPostfixLabel = new JLabel("PLN");
        paymentMethodLabel = new JLabel(Reference.getString("label.operation.payment_method"));
        invoiceIdLabel = new JLabel(Reference.getString("label.operation.invoice"));
        okButton = new JButton(Reference.getString("button.ok"));

        dateInput = new JTextField();
        contractorInput = new JComboBox<>(databaseAccessor.getContractorsVector());
        descriptionInput = new JTextField(20);
        amountInput = new JTextField();
        paymentMethodInput = new JComboBox<>(PaymentMethod.values());
        invoiceIdInput = new JTextField();

        register(dateLabel, Utils.gridPosition(0, 0));
        register(dateInput, Utils.gridPosition(0, 1));
        register(contractorLabel, Utils.gridPosition(1, 0));
        register(contractorInput, Utils.gridPosition(1, 1));
        register(descriptionLabel, Utils.gridPosition(2, 0));
        register(descriptionInput, Utils.gridPosition(2, 1));
        register(amountLabel, Utils.gridPosition(3, 0));
        register(amountInput, Utils.gridPosition(3, 1));
        register(amountPostfixLabel, Utils.gridPosition(3, 2));
        register(invoiceIdLabel, Utils.gridPosition(4, 0));
        register(invoiceIdInput, Utils.gridPosition(4, 1));
        if (editedObject instanceof Consideration) {
            register(paymentMethodLabel, Utils.gridPosition(5, 0));
            register(paymentMethodInput, Utils.gridPosition(5, 1));
        }

        dateInput.setText(editedObject != null ? editedObject.getDate() : "");
        contractorInput.setSelectedItem(editedObject != null ?
                                        databaseAccessor.getObject(editedObject.getContractorUuid()) : null);
        descriptionInput.setText(editedObject != null ? editedObject.getDescription() : "");
        amountInput.setText(editedObject != null ? new Monetary(editedObject.getAmount()).toString() : "");
        if (editedObject != null) {
            final Invoice invoice = (Invoice) databaseAccessor.getObject(editedObject.getInvoiceUuid());
            invoiceIdInput.setText(invoice != null ? invoice.getId() : "");
        }
        if (editedObject instanceof Consideration)
            paymentMethodInput.setSelectedItem(PaymentMethod.get(((Consideration) editedObject).getPaymentMethodUuid()));

        final AbstractValidInputListener textFieldListener = createTextInputValidator();
        dateInput.getDocument().addDocumentListener(textFieldListener);
        descriptionInput.getDocument().addDocumentListener(textFieldListener);
        amountInput.getDocument().addDocumentListener(textFieldListener);
        invoiceIdInput.getDocument().addDocumentListener(textFieldListener);

        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
        okButton.setEnabled(textFieldListener.dataValid());
    }

    private AbstractValidInputListener createTextInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            public boolean dataValid() {

                final boolean dateValid = InputValidator.isValidDate(dateInput.getText()),
                        descriptionValid = !descriptionInput.getText().equals(""),
                        amountValid = InputValidator.isValidNumber(amountInput.getText()),
                        invoiceIdValid = InputValidator.isExistingInvoiceId(invoiceIdInput.getText(), databaseAccessor);

                if (!dateValid && !dateInput.getText().equals(""))
                    displayError(dateInput, Reference.getString("input.invalid.date"));
                else cancelError(dateInput);
                if (!amountValid && !amountInput.getText().equals(""))
                    displayError(amountInput, Reference.getString("input.invalid.number"));
                else cancelError(amountInput);
                if (!invoiceIdValid && !invoiceIdInput.getText().equals(""))
                    displayError(invoiceIdInput, Reference.getString("input.invalid.invoice_id"));
                else cancelError(invoiceIdInput);

                return dateValid && descriptionValid && amountValid && (invoiceIdValid || invoiceIdInput.getText().equals(""));
            }
        };
    }
}
