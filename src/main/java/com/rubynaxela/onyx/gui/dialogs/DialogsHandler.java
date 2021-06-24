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
import com.rubynaxela.onyx.data.datatypes.InvoiceItem;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;
import java.util.Objects;
import java.util.UUID;

public class DialogsHandler {

    private final JFrame anchor;
    private final DatabaseAccessor databaseAccessor;

    public DialogsHandler(JFrame anchor, DatabaseAccessor databaseAccessor) {
        this.anchor = anchor;
        this.databaseAccessor = databaseAccessor;
    }

    public void showError(String message, boolean exitOnClose) {
        final String okButtonText = "OK";
        Object okButton;
        if (exitOnClose) {
            okButton = new JButton(okButtonText);
            ((JButton) okButton).addActionListener(e -> System.exit(0));
        } else
            okButton = okButtonText;
        JOptionPane.showOptionDialog(anchor, message, "Błąd", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                                     Reference.getIcon("dialog.error"), new Object[]{okButton}, okButton);
    }

    public boolean askYesNoQuestion(String message, boolean defaultAnswer) {
        return JOptionPane.showOptionDialog(anchor, message, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                            Reference.getIcon("dialog.question"), new String[]{"Tak", "Nie"},
                                            defaultAnswer ? "Tak" : "Nie") == 0;
    }

    public Contractor showContractorDialog(Contractor editedElement) {
        final ContractorDialogPanel dialogPanel = new ContractorDialogPanel(editedElement);
        String title;
        if (editedElement == null) title = "Dodawanie kontrahenta";
        else title = "Edycja kontrahenta";
        return (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                             new Object[]{dialogPanel.okButton, "Anuluj"}, dialogPanel.okButton) == 0) ?
               new Contractor(editedElement == null ? UUID.randomUUID().toString() : editedElement.getUuid(),
                              dialogPanel.nameInput.getText(), dialogPanel.detailsInput.getText()) : null;
    }

    public Invoice showInvoiceDialog(Invoice editedElement) {
        final InvoiceDialogPanel dialogPanel = new InvoiceDialogPanel(editedElement, databaseAccessor);
        String title;
        if (editedElement == null) title = "Dodawanie rachunku";
        else title = "Edycja rachunku";
        return (JOptionPane.showOptionDialog(anchor, dialogPanel, title, JOptionPane.YES_NO_OPTION,
                                             JOptionPane.QUESTION_MESSAGE, Reference.getIcon("dialog.data"),
                                             new Object[]{dialogPanel.okButton, "Anuluj"}, dialogPanel.okButton) == 0) ?
               new ClosedInvoice(editedElement == null ? UUID.randomUUID().toString() : editedElement.getUuid(),
                                 dialogPanel.idInput.getText(), dialogPanel.dateInput.getText(),
                                 ((Contractor) Objects.requireNonNull(dialogPanel.contractorInput.getSelectedItem())).getUuid(),
                                 editedElement == null ? new InvoiceItem[0] : dialogPanel.getInvoiceItems()) : null;
    }
}
