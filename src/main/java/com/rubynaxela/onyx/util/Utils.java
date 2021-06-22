/*
 * Copyright (c) 2021 Jacek Pawelski a.k.a. RubyNaxela
 *
 * Licensed under the GNU General Public License v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * The license is included in file 'LICENSE.txt', which is part of this
 * source code package. You may also obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
    public static GridBagConstraints gridElementSettings(int row, int column, int width, int height, int alignment) {
        return gridElementSettings(row, column, width, height, alignment,
                                   new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column, int width, int height, int alignment,
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
    public static GridBagConstraints gridElementSettings(int row, int column, int width, int height) {
        return gridElementSettings(row, column, width, height, GridBagConstraints.CENTER,
                                   new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }

    @Contract(pure = true)
    public static GridBagConstraints gridElementSettings(int row, int column, int alignment) {
        return gridElementSettings(row, column, 1, 1, alignment,
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
    public static GridBagConstraints gridElementSettings(int row, int column) {
        return gridElementSettings(row, column, 1, 1, GridBagConstraints.CENTER,
                                   new Insets(5, 5, 5, 5), GridBagConstraints.BOTH);
    }
}
