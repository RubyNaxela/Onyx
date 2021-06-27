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
import com.rubynaxela.onyx.data.datatypes.auxiliary.LeafLabel;
import com.rubynaxela.onyx.gui.components.*;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.dnd.*;

/**
 * This class represents the structure of the application main window GUI
 */
public final class MainWindow extends DefaultJFrame implements DropTargetListener {

    public static final LeafLabel
            contractorsLabel = new LeafLabel(Reference.getString("navigation.contractors")),
            openInvoicesLabel = new LeafLabel(Reference.getString("navigation.invoices.unaccounted")),
            closedInvoicesLabel = new LeafLabel(Reference.getString("navigation.invoices.cleared")),
            claimsLabel = new LeafLabel(Reference.getString("navigation.operations.transactions.claims")),
            liabilitiesLabel = new LeafLabel(Reference.getString("navigation.operations.transactions.liabilities")),
            contributionsLabel = new LeafLabel(Reference.getString("navigation.operations.considerations.contributions")),
            paymentsLabel = new LeafLabel(Reference.getString("navigation.operations.considerations.payments"));
    public final JTree navigation;

    public final DefaultJButton
            addButton = new DefaultJButton(Reference.getString("button.add")),
            editButton = new DefaultJButton(Reference.getString("button.edit")),
            removeButton = new DefaultJButton(Reference.getString("button.remove"));

    public final StaticJTable dataTable;
    public final OnyxTableModel dataTableModel;

    private FileDropListener fileDropListener;

    public MainWindow(String title, DatabaseAccessor databaseAccessor) {
        super(true);
        setTitle(title);

        dataTable = new StaticJTable();
        dataTableModel = new OnyxTableModel(databaseAccessor, dataTable, addButton, editButton, removeButton);
        dataTableModel.addTableModelListener(e -> dataTable.resizeColumnWidth(15, 300));

        final JPanel navigationButtonsPanelWrapper = new JPanel();
        register(navigationButtonsPanelWrapper, Utils.gridElementSettings(0, 0));
        {
            final DefaultJPanel navigationButtonsPanel = new DefaultJPanel();
            navigationButtonsPanelWrapper.add(navigationButtonsPanel);
            {
                final DefaultMutableTreeNode root = new DefaultMutableTreeNode(databaseAccessor.getCompanyName());
                navigation = new JTree(root);
                navigation.setShowsRootHandles(true);
                {
                    final DefaultMutableTreeNode contractors = new DefaultMutableTreeNode(contractorsLabel);
                    root.add(contractors);
                    final DefaultMutableTreeNode invoices = new DefaultMutableTreeNode(
                            Reference.getString("navigation.invoices"));
                    root.add(invoices);
                    {
                        final DefaultMutableTreeNode openInvoices = new DefaultMutableTreeNode(openInvoicesLabel);
                        invoices.add(openInvoices);
                        final DefaultMutableTreeNode closedInvoices = new DefaultMutableTreeNode(closedInvoicesLabel);
                        invoices.add(closedInvoices);
                    }
                    final DefaultMutableTreeNode allOperations = new DefaultMutableTreeNode(
                            Reference.getString("navigation.operations"));
                    root.add(allOperations);
                    {
                        final DefaultMutableTreeNode transactions = new DefaultMutableTreeNode(
                                Reference.getString("navigation.operations.transactions"));
                        allOperations.add(transactions);
                        {
                            final DefaultMutableTreeNode claims = new DefaultMutableTreeNode(claimsLabel);
                            transactions.add(claims);
                            final DefaultMutableTreeNode liabilities = new DefaultMutableTreeNode(liabilitiesLabel);
                            transactions.add(liabilities);
                        }
                        final DefaultMutableTreeNode considerations = new DefaultMutableTreeNode(
                                Reference.getString("navigation.operations.considerations"));
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
            viewPanel.register(new DefaultJScrollPane(dataTable, 600, 350),
                               Utils.gridElementSettings(1, 0));
            dataTableModel.setupTable();
        }
        pack();

        new DropTarget(this, DnDConstants.ACTION_COPY, this);
    }

    public void setFileDropListener(FileDropListener listener) {
        this.fileDropListener = listener;
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
