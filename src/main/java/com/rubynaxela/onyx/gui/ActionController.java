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

import javax.swing.*;
import java.awt.event.ActionListener;

public class ActionController {

    public final JButton button;
    public final JMenuItem menuItem;
    private ActionListener listener;

    public ActionController(JButton button, JMenuItem menuItem) {
        this.button = button;
        this.menuItem = menuItem;
    }

    public void setListener(ActionListener listener) {
        this.listener = listener;
        button.addActionListener(listener);
        menuItem.addActionListener(listener);
    }

    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
        menuItem.setEnabled(enabled);
    }

    public void setVisible(boolean enabled) {
        button.setVisible(enabled);
        menuItem.setEnabled(enabled);
    }

    public void perform() {
        listener.actionPerformed(null);
    }
}
