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

import net.java.balloontip.BalloonTip;
import net.java.balloontip.styles.RoundedBalloonStyle;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.HashMap;

public abstract class AbstractValidInputListener implements DocumentListener {

    private static final Border
            defaultBorder = new JTextField().getBorder(),
            errorBorder = BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0x834141), 3, true),
                    BorderFactory.createEmptyBorder(2, 6, 2, 6)),
            errorScrollPaneBorder = BorderFactory.createLineBorder(new Color(0x834141), 3, true);
    private final JButton okButton;
    private final HashMap<JComponent, BalloonTip> errorTooltips;

    protected AbstractValidInputListener(JButton okButton) {
        this.okButton = okButton;
        this.errorTooltips = new HashMap<>();
    }

    protected void displayError(JComponent inputComponent, String message) {
        if (!errorTooltips.containsKey(inputComponent)) {
            final BalloonTip errorTooltip
                    = new BalloonTip(inputComponent, "<html>" + message.replace("\n", "<br>") + "</html>",
                                     new RoundedBalloonStyle(5, 5, new Color(0x834141),
                                                             inputComponent.getForeground()), false);
            errorTooltips.put(inputComponent, errorTooltip);
            if (!(inputComponent instanceof JScrollPane)) inputComponent.setBorder(errorBorder);
            else inputComponent.setBorder(errorScrollPaneBorder);
        }
    }

    protected void cancelError(JComponent... components) {
        for (JComponent component : components)
            if (errorTooltips.containsKey(component)) {
                errorTooltips.get(component).closeBalloon();
                errorTooltips.remove(component);
                component.setBorder(defaultBorder);
            }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        okButton.setEnabled(dataValid());
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        okButton.setEnabled(dataValid());
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        okButton.setEnabled(dataValid());
    }

    public abstract boolean dataValid();
}
