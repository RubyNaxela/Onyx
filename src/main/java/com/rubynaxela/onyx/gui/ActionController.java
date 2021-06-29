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

package com.rubynaxela.onyx.gui;

import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActionController {

    public final JButton button;
    public final JMenuItem menuItem;
    private ActionListener listener;

    public ActionController(@Nullable JButton button, @Nullable JMenuItem menuItem) {
        this.button = button;
        this.menuItem = menuItem;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
        if (button != null) button.addActionListener(listener);
        if (menuItem != null) menuItem.addActionListener(listener);
    }

    public void setEnabled(boolean enabled) {
        if (button != null) button.setEnabled(enabled);
        if (menuItem != null) menuItem.setEnabled(enabled);
    }

    public void setVisible(boolean enabled) {
        if (button != null) button.setVisible(enabled);
        if (menuItem != null) menuItem.setEnabled(enabled);
    }

    public void perform() {
        listener.actionPerformed(null);
    }
}
