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

import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;

public class MessageDialogsHandler {

    private final JFrame anchor;

    public MessageDialogsHandler(JFrame anchor) {
        this.anchor = anchor;
    }

    public void showError(String msg, boolean critical) {
        final String okButtonText = Reference.getString("button.ok", "OK");
        Object okButton;
        if (critical) {
            okButton = new JButton(okButtonText);
            ((JButton) okButton).addActionListener(e -> System.exit(0));
        } else
            okButton = okButtonText;
        JOptionPane.showOptionDialog(anchor, msg, Reference.getString("title.dialog.error", "Error"),
                                     JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE,
                                     Reference.getIcon("dialog.error"), new Object[]{okButton}, okButton);
    }

    public boolean askYesNoQuestion(String msg, boolean defaultAnswer) {
        return JOptionPane.showOptionDialog(anchor, msg, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                            Reference.getIcon("dialog.question"), new String[]{
                        Reference.getString("button.yes", "Yes"),
                        Reference.getString("button.no", "No")
                }, defaultAnswer ? "Tak" : "Nie") == 0;
    }
}
