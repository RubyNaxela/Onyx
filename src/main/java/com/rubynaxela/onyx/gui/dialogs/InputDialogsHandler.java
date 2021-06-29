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
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectType;
import com.rubynaxela.onyx.data.datatypes.auxiliary.PaymentMethod;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.*;
import com.rubynaxela.onyx.util.Reference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;
import java.util.UUID;

public final class InputDialogsHandler {

    private final JFrame anchor;
    private final DatabaseAccessor databaseAccessor;

    public InputDialogsHandler(JFrame anchor, DatabaseAccessor databaseAccessor) {
        this.anchor = anchor;
        this.databaseAccessor = databaseAccessor;
    }

    @Nullable
    public Contractor showContractorDialog(@Nullable Contractor editedObject) {
        final ContractorDialogPanel dialogPanel = new ContractorDialogPanel(editedObject);
        final String title = editedObject == null ? Reference.getString("title.dialog.contractor.new")
                                                  : Reference.getString("title.dialog.contractor.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            return new Contractor(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                                  dialogPanel.nameInput.getText(),
                                  dialogPanel.detailsInput.getText());
        else return null;
    }

    @Nullable
    public Invoice showInvoiceDialog(@Nullable Invoice editedObject) {
        final InvoiceDialogPanel dialogPanel = new InvoiceDialogPanel(editedObject, databaseAccessor);
        final String title = editedObject == null ? Reference.getString("title.dialog.invoice.new")
                                                  : Reference.getString("title.dialog.invoice.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            if (!dialogPanel.clearedCheckBox.isSelected())
                return new OpenInvoice(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                                       dialogPanel.idInput.getText(),
                                       dialogPanel.dateInput.getText(),
                                       ((Contractor) Objects.requireNonNull(
                                               dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                       dialogPanel.getInvoiceItems());
            else return new ClosedInvoice(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                                          dialogPanel.idInput.getText(),
                                          dialogPanel.dateInput.getText(),
                                          ((Contractor) Objects.requireNonNull(
                                                  dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                          dialogPanel.getInvoiceItems());

        else return null;
    }

    @Nullable
    public Claim showClaimDialog(@Nullable Claim editedObject) {
        final OperationDialogPanel dialogPanel = new OperationDialogPanel(editedObject, databaseAccessor);
        final String title = editedObject == null ? Reference.getString("title.dialog.operation.new")
                                                  : Reference.getString("title.dialog.operation.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            return new Claim(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                             dialogPanel.dateInput.getText(),
                             ((Contractor) Objects.requireNonNull(dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                             dialogPanel.descriptionInput.getText(),
                             new Monetary(dialogPanel.amountInput.getText()).toDouble());
        else return null;
    }

    @Nullable
    public Liability showLiabilityDialog(@Nullable Liability editedObject) {
        final OperationDialogPanel dialogPanel = new OperationDialogPanel(editedObject, databaseAccessor);
        final String title = editedObject == null ? Reference.getString("title.dialog.operation.new")
                                                  : Reference.getString("title.dialog.operation.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            return new Liability(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                                 dialogPanel.dateInput.getText(),
                                 ((Contractor) Objects.requireNonNull(dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                 dialogPanel.descriptionInput.getText(),
                                 new Monetary(dialogPanel.amountInput.getText()).toDouble());
        else return null;
    }

    @Nullable
    public Contribution showContributionDialog(@Nullable Contribution editedObject) {
        final OperationDialogPanel dialogPanel = new OperationDialogPanel(editedObject, databaseAccessor);
        final String title = editedObject == null ? Reference.getString("title.dialog.operation.new")
                                                  : Reference.getString("title.dialog.operation.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            return new Contribution(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                                    dialogPanel.dateInput.getText(),
                                    ((Contractor) Objects.requireNonNull(
                                            dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                    dialogPanel.descriptionInput.getText(),
                                    new Monetary(dialogPanel.amountInput.getText()).toDouble(),
                                    ((PaymentMethod) Objects.requireNonNull(
                                            dialogPanel.paymentMethodInput.getSelectedItem())).getUuid(),
                                    editedObject != null ? editedObject.getInvoiceUuid() : null);
        else return null;
    }

    @Nullable
    public Payment showPaymentDialog(@Nullable Payment editedObject) {
        final OperationDialogPanel dialogPanel = new OperationDialogPanel(editedObject, databaseAccessor);
        final String title = editedObject == null ? Reference.getString("title.dialog.operation.new")
                                                  : Reference.getString("title.dialog.operation.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            return new Payment(editedObject == null ? UUID.randomUUID().toString() : editedObject.getUuid(),
                               dialogPanel.dateInput.getText(),
                               ((Contractor) Objects.requireNonNull(dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                               dialogPanel.descriptionInput.getText(),
                               new Monetary(dialogPanel.amountInput.getText()).toDouble(),
                               ((PaymentMethod) Objects.requireNonNull(
                                       dialogPanel.paymentMethodInput.getSelectedItem())).getUuid(),
                               editedObject != null ? editedObject.getInvoiceUuid() : null);
        else return null;
    }

    @Nullable
    public Identifiable showObjectDialog(@NotNull ObjectType type, @Nullable Identifiable editedObject) {
        final Identifiable object;
        if (type == ObjectType.CONTRACTOR) object = showContractorDialog((Contractor) editedObject);
        else if (type == ObjectType.OPEN_INVOICE ||
                 type == ObjectType.CLOSED_INVOICE) object = showInvoiceDialog((Invoice) editedObject);
        else if (type == ObjectType.CLAIM) object = showClaimDialog((Claim) editedObject);
        else if (type == ObjectType.LIABILITY) object = showLiabilityDialog((Liability) editedObject);
        else if (type == ObjectType.CONTRIBUTION) object = showContributionDialog((Contribution) editedObject);
        else if (type == ObjectType.PAYMENT) object = showPaymentDialog((Payment) editedObject);
        else object = null;
        return object;
    }
}
