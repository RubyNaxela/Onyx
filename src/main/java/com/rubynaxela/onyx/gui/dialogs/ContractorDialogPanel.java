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

import com.rubynaxela.onyx.data.datatypes.databaseobjects.Contractor;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.event.DocumentListener;

public class ContractorDialogPanel extends DefaultJPanel {

    public final JLabel nameLabel, detailsLabel;
    public final JTextField nameInput;
    public final JTextArea detailsInput;
    public final JButton okButton;

    public ContractorDialogPanel(Contractor editedElement) {

        nameLabel = new JLabel(Reference.getString("label.contractor.name"));
        detailsLabel = new JLabel(Reference.getString("label.contractor.details"));
        nameInput = new JTextField();
        detailsInput = new JTextArea(5, 30);
        okButton = new JButton(Reference.getString("button.ok"));

        register(nameLabel, Utils.gridElementSettings(0, 0));
        register(nameInput, Utils.gridElementSettings(0, 1));
        register(detailsLabel, Utils.gridElementSettings(1, 0, 2, 1));
        detailsInput.setLineWrap(true);
        detailsInput.setWrapStyleWord(true);
        register(detailsInput, Utils.gridElementSettings(2, 0, 2, 1));

        nameInput.setText(editedElement != null ? editedElement.getName() : "");
        detailsInput.setText(editedElement != null ? editedElement.getDetails() : "");

        final DocumentListener textFieldListener = createInputValidator();
        nameInput.getDocument().addDocumentListener(textFieldListener);
        detailsInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedElement != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
    }

    private AbstractValidInputListener createInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            protected boolean dataValid() {
                return !nameInput.getText().equals("")
                       && !detailsInput.getText().equals("");
            }
        };
    }
}
