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

package com.rubynaxela.onyx.util;

import org.jetbrains.annotations.Contract;

import javax.swing.*;
import java.awt.*;

public final class Utils {

    public static JOptionPane getOptionPane(JComponent component) {
        return component instanceof JOptionPane ? (JOptionPane) component : getOptionPane((JComponent) component.getParent());
    }

    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column, int width, int height, int alignment,
                                                  Insets insets, int fill) {
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = column;
        gbc.gridy = row;
        gbc.anchor = alignment;
        gbc.insets = insets;
        gbc.fill = fill;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        return gbc;
    }

    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column, int width, int height) {
        return gridPosition(row, column, width, height, GridBagConstraints.CENTER,
                            new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column) {
        return gridPosition(row, column, 1, 1, GridBagConstraints.CENTER,
                            new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }
}
