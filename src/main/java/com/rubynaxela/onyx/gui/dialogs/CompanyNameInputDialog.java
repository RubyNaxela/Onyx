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

import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;

public class CompanyNameInputDialog extends DefaultJPanel {

    public final JLabel nameLabel;
    public final JTextField nameInput;
    public final JButton okButton;

    public CompanyNameInputDialog(String name) {

        nameLabel = new JLabel();
        nameInput = new JTextField();
        okButton = new JButton(Reference.getString("button.ok"));

        register(nameLabel, Utils.gridPosition(0, 0));
        register(nameInput, Utils.gridPosition(1, 0));

        nameLabel.setText(Reference.getString("label.company_name"));
        nameInput.setText(name);

        final AbstractValidInputListener textFieldListener = createTextInputValidator();
        nameInput.getDocument().addDocumentListener(textFieldListener);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
        okButton.setEnabled(textFieldListener.dataValid());
    }

    private AbstractValidInputListener createTextInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            public boolean dataValid() {
                return !nameInput.getText().equals("");
            }
        };
    }
}
