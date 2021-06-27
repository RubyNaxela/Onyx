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
import com.rubynaxela.onyx.data.datatypes.ClosedInvoice;
import com.rubynaxela.onyx.data.datatypes.Contractor;
import com.rubynaxela.onyx.data.datatypes.Invoice;
import com.rubynaxela.onyx.data.datatypes.OpenInvoice;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;
import java.util.Objects;
import java.util.UUID;

public class InputDialogsHandler {

    private final JFrame anchor;
    private final DatabaseAccessor databaseAccessor;

    public InputDialogsHandler(JFrame anchor, DatabaseAccessor databaseAccessor) {
        this.anchor = anchor;
        this.databaseAccessor = databaseAccessor;
    }

    public Contractor showContractorDialog(Contractor editedElement) {
        final ContractorDialogPanel dialogPanel = new ContractorDialogPanel(editedElement);
        final String title = editedElement == null ? Reference.getString("title.dialog.contractor.new")
                                                   : Reference.getString("title.dialog.contractor.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            return new Contractor(editedElement == null ? UUID.randomUUID().toString() : editedElement.getUuid(),
                                  dialogPanel.nameInput.getText(),
                                  dialogPanel.detailsInput.getText());
        else return null;
    }

    public Invoice showInvoiceDialog(Invoice editedElement) {
        final InvoiceDialogPanel dialogPanel = new InvoiceDialogPanel(editedElement, databaseAccessor);
        final String title = editedElement == null ? Reference.getString("title.dialog.invoice.new")
                                                   : Reference.getString("title.dialog.invoice.edit");
        if (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                         JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                         new Object[]{dialogPanel.okButton, Reference.getString("button.cancel")},
                                         dialogPanel.okButton) == 0)
            if (!dialogPanel.clearedCheckBox.isSelected()) {
                return new OpenInvoice(editedElement == null ? UUID.randomUUID().toString() : editedElement.getUuid(),
                                       dialogPanel.idInput.getText(),
                                       dialogPanel.dateInput.getText(),
                                       ((Contractor) Objects.requireNonNull(
                                               dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                       dialogPanel.getInvoiceItems());
            } else {
                return new ClosedInvoice(editedElement == null ? UUID.randomUUID().toString() : editedElement.getUuid(),
                                         dialogPanel.idInput.getText(),
                                         dialogPanel.dateInput.getText(),
                                         ((Contractor) Objects.requireNonNull(
                                                 dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                         dialogPanel.getInvoiceItems());
            }
        else return null;
    }
}
