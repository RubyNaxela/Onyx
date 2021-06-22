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
import com.rubynaxela.onyx.data.datatypes.Contractor;
import com.rubynaxela.onyx.data.datatypes.Invoice;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.event.DocumentListener;

public class InvoiceDialogPanel extends DefaultJPanel {

    public final JLabel idLabel, dateLabel, contractorLabel;
    public final JTextField idInput, dateInput;
    public final JComboBox<Contractor> contractorInput;
    public final JButton okButton;

    public InvoiceDialogPanel(Invoice editedElement, DatabaseAccessor databaseAccessor) {

        idLabel = new JLabel("Numer");
        dateLabel = new JLabel("Data");
        contractorLabel = new JLabel("Kontrahent");
        idInput = new JTextField();
        dateInput = new JTextField();
        contractorInput = new JComboBox<>(databaseAccessor.getContractorsVector());
        okButton = new JButton("OK");

        register(idLabel, Utils.gridElementSettings(0, 0));
        register(idInput, Utils.gridElementSettings(0, 1));
        register(dateLabel, Utils.gridElementSettings(1, 0));
        register(dateInput, Utils.gridElementSettings(1, 1));
        register(contractorLabel, Utils.gridElementSettings(2, 0, 2, 1));
        register(contractorInput, Utils.gridElementSettings(3, 0, 2, 1));

        idInput.setText(editedElement != null ? editedElement.getId() : "");
        dateInput.setText(editedElement != null ? editedElement.getDate() : "");

        final DocumentListener textFieldListener = createInputValidator();
        idInput.getDocument().addDocumentListener(textFieldListener);
        dateInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedElement != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
    }

    private AbstractValidInputListener createInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            protected boolean dataValid() {
                return !idInput.getText().equals("")
                       && !dateInput.getText().equals("");
            }
        };
    }
}
