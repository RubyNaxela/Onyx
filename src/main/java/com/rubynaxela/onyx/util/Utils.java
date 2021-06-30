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

    /**
     * Provides a reference to the {@link JOptionPane} parent of a given {@link JComponent} element
     *
     * @param component a dialog component
     * @return the {@code JOptionPane} that a given element is a child of
     */
    public static JOptionPane getOptionPane(JComponent component) {
        return component instanceof JOptionPane ? (JOptionPane) component : getOptionPane((JComponent) component.getParent());
    }

    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column, int width, int height, int alignment) {
        return gridPosition(row, column, width, height, alignment,
                            new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
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

    /**
     * Returns a {@link java.awt.GridBagConstraints} object with the following properties:
     * <ul>
     *     <li>{@code row} and {@code column} parameters translate directly into {@link java.awt.GridBagConstraints#gridy}
     *         and {@link java.awt.GridBagConstraints#gridx} fields of the returned object respectively</li>
     *     <li>the {@code width} and {@code height} parameter translates to {@link java.awt.GridBagConstraints#gridwidth}
     *         and fields {@link java.awt.GridBagConstraints#gridheight} fields of the returned object respectively,
     *         which means it will take up space of that much grid columns and rows</li>
     * </ul>
     *
     * @param row    vertical position in the grid
     * @param column horizontal position in the grid
     * @param width  determines the component width (in terms of grid columns)
     * @param height determines the component height (in terms of grid rows)
     * @return a {@link java.awt.GridBagConstraints} instance
     */
    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column, int width, int height) {
        return gridPosition(row, column, width, height, GridBagConstraints.CENTER,
                            new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column, int alignment) {
        return gridPosition(row, column, 1, 1, alignment,
                            new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    /**
     * Returns a {@link GridBagConstraints} object where {@code row} and {@code column} parameters translate directly into
     * {@code gridy} and {@code gridx} fields of the returned object respectively
     *
     * @param row    vertical position in the grid
     * @param column horizontal position in the grid
     * @return a {@code GridBagConstraints} instance
     */
    @Contract(pure = true)
    public static GridBagConstraints gridPosition(int row, int column) {
        return gridPosition(row, column, 1, 1, GridBagConstraints.CENTER,
                            new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }
}
