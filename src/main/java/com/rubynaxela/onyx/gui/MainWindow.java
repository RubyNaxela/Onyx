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

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.datatypes.Identifiable;
import com.rubynaxela.onyx.data.datatypes.auxiliary.NavigationLeafNode;
import com.rubynaxela.onyx.data.datatypes.auxiliary.OnyxTableModel;
import com.rubynaxela.onyx.gui.components.*;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.dnd.*;

/**
 * This class represents the structure of the application main window GUI
 */
public final class MainWindow extends DefaultJFrame implements DropTargetListener {

    public static final NavigationLeafNode
            contractorsLabel = new NavigationLeafNode("Kontrahenci"),
            openInvoicesLabel = new NavigationLeafNode("Nierozliczone"),
            closedInvoicesLabel = new NavigationLeafNode("Rozliczone"),
            claimsLabel = new NavigationLeafNode("Należności"),
            liabilitiesLabel = new NavigationLeafNode("Zobowiązania"),
            contributionsLabel = new NavigationLeafNode("Wpłaty"),
            paymentsLabel = new NavigationLeafNode("Wypłaty");
    public final JTree navigation;

    public final DefaultJButton
            addButton = new DefaultJButton("Dodaj"),
            editButton = new DefaultJButton("Edytuj"),
            removeButton = new DefaultJButton("Usuń");

    public final StaticJTable dataTable = new StaticJTable();
    public final OnyxTableModel dataTableModel;
    public Identifiable currentElement;

    private FileDropListener fileDropListener;

    public MainWindow(String title, DatabaseAccessor databaseAccessor) {
        super(true);
        setTitle(title);

        dataTableModel = new OnyxTableModel(databaseAccessor);
        dataTableModel.addTableModelListener(e -> resizeColumnWidth(15, 300));

        final JPanel navigationButtonsPanelWrapper = new JPanel();
        register(navigationButtonsPanelWrapper, Utils.gridElementSettings(0, 0));
        {
            final DefaultJPanel navigationButtonsPanel = new DefaultJPanel();
            navigationButtonsPanelWrapper.add(navigationButtonsPanel);
            {
                final DefaultMutableTreeNode root = new DefaultMutableTreeNode("RubyNaxela");
                navigation = new JTree(root);
                navigation.setShowsRootHandles(true);
                {
                    final DefaultMutableTreeNode contractors = new DefaultMutableTreeNode(contractorsLabel);
                    root.add(contractors);
                    final DefaultMutableTreeNode invoices = new DefaultMutableTreeNode("Faktury");
                    root.add(invoices);
                    {
                        final DefaultMutableTreeNode openInvoices = new DefaultMutableTreeNode(openInvoicesLabel);
                        invoices.add(openInvoices);
                        final DefaultMutableTreeNode closedInvoices = new DefaultMutableTreeNode(closedInvoicesLabel);
                        invoices.add(closedInvoices);
                    }
                    final DefaultMutableTreeNode allOperations = new DefaultMutableTreeNode("Księgowość");
                    root.add(allOperations);
                    {
                        final DefaultMutableTreeNode transactions = new DefaultMutableTreeNode("Rozrachunki");
                        allOperations.add(transactions);
                        {
                            final DefaultMutableTreeNode claims = new DefaultMutableTreeNode(claimsLabel);
                            transactions.add(claims);
                            final DefaultMutableTreeNode liabilities = new DefaultMutableTreeNode(liabilitiesLabel);
                            transactions.add(liabilities);
                        }
                        final DefaultMutableTreeNode considerations = new DefaultMutableTreeNode("Rozliczenia");
                        allOperations.add(considerations);
                        {
                            final DefaultMutableTreeNode contributions = new DefaultMutableTreeNode(contributionsLabel);
                            considerations.add(contributions);
                            final DefaultMutableTreeNode payments = new DefaultMutableTreeNode(paymentsLabel);
                            considerations.add(payments);
                        }
                    }
                }
                navigationButtonsPanel.register(new DefaultJScrollPane(navigation, 180, 407),
                                                Utils.gridElementSettings(0, 0));
            }
        }

        final DefaultJPanel viewPanel = new DefaultJPanel();
        register(viewPanel, Utils.gridElementSettings(0, 1));
        {
            final JPanel viewPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            viewPanel.register(viewPanelWrapper, Utils.gridElementSettings(0, 0));
            {
                final DefaultJPanel editorButtonsPanel = new DefaultJPanel();
                viewPanelWrapper.add(editorButtonsPanel);
                {
                    addButton.setEnabled(false);
                    editorButtonsPanel.register(addButton, Utils.gridElementSettings(0, 0));
                    editButton.setEnabled(false);
                    editorButtonsPanel.register(editButton, Utils.gridElementSettings(0, 1));
                    removeButton.setEnabled(false);
                    editorButtonsPanel.register(removeButton, Utils.gridElementSettings(0, 2));
                }
            }
            dataTable.setModel(dataTableModel);
            viewPanel.register(new DefaultJScrollPane(dataTable, 600, 350),
                               Utils.gridElementSettings(1, 0));
        }
        pack();

        new DropTarget(this, DnDConstants.ACTION_COPY, this);
    }

    public void setFileDropListener(FileDropListener listener) {
        this.fileDropListener = listener;
    }

    public void resizeColumnWidth(int minWidth, int maxWidth) {
        final TableColumnModel columnModel = dataTable.getColumnModel();
        for (int column = 0; column < dataTable.getColumnCount(); column++) {
            int width = minWidth;
            for (int row = 0; row < dataTable.getRowCount(); row++) {
                final TableCellRenderer renderer = dataTable.getCellRenderer(row, column);
                width = Math.max(dataTable.prepareRenderer(renderer, row, column).getPreferredSize().width + 1, width);
            }
            if (width > maxWidth) width = maxWidth;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Override
    public void dragEnter(DropTargetDragEvent e) {
    }

    @Override
    public void dragOver(DropTargetDragEvent e) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent e) {
    }

    @Override
    public void dragExit(DropTargetEvent e) {
    }

    @Override
    public void drop(DropTargetDropEvent e) {
        fileDropListener.handleDropEvent(e);
    }
}