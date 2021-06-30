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

package com.rubynaxela.onyx.gui.components;

import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class StaticJTable extends JTable {

    private final boolean cellsEditable;

    public StaticJTable() {
        this(false);
    }

    StaticJTable(boolean cellsEditable) {
        DefaultListSelectionModel brandsListModel = new DefaultListSelectionModel();
        brandsListModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setSelectionModel(brandsListModel);
        this.getTableHeader().setReorderingAllowed(false);
        this.cellsEditable = cellsEditable;
    }

    public void alignColumn(int index, @MagicConstant(intValues = {JLabel.LEFT, JLabel.CENTER, JLabel.RIGHT}) int alignment) {
        final DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(alignment);
        this.getColumnModel().getColumn(index).setCellRenderer(renderer);
    }

    @SuppressWarnings("SameParameterValue")
    public void resizeColumnWidth(int minWidth, int maxWidth) {
        final TableColumnModel columnModel = getColumnModel();
        for (int column = 0; column < getColumnCount(); column++) {
            int width = minWidth;
            for (int row = 0; row < getRowCount(); row++) {
                final TableCellRenderer renderer = getCellRenderer(row, column);
                width = Math.max(prepareRenderer(renderer, row, column).getPreferredSize().width + 1, width);
            }
            if (width > maxWidth) width = maxWidth;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return cellsEditable;
    }
}
