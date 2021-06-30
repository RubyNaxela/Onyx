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
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectType;
import com.rubynaxela.onyx.gui.components.*;
import com.rubynaxela.onyx.util.Reference;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

/**
 * This class holds the application main window
 */
public final class MainWindow extends DefaultJFrame {

    public static final LeafLabel
            contractorsLabel = new LeafLabel(Reference.getString("navigation.contractors"),
                                             ObjectType.CONTRACTOR),
            openInvoicesLabel = new LeafLabel(Reference.getString("navigation.invoices.unaccounted"),
                                              ObjectType.OPEN_INVOICE),
            closedInvoicesLabel = new LeafLabel(Reference.getString("navigation.invoices.cleared"),
                                                ObjectType.CLOSED_INVOICE),
            claimsLabel = new LeafLabel(Reference.getString("navigation.operations.transactions.claims"),
                                        ObjectType.CLAIM),
            liabilitiesLabel = new LeafLabel(Reference.getString("navigation.operations.transactions.liabilities"),
                                             ObjectType.LIABILITY),
            contributionsLabel = new LeafLabel(Reference.getString("navigation.operations.considerations.contributions"),
                                               ObjectType.CONTRIBUTION),
            paymentsLabel = new LeafLabel(Reference.getString("navigation.operations.considerations.payments"),
                                          ObjectType.PAYMENT);

    public final MenuBar menuBar = new MenuBar();
    public final DefaultJTree navigation;
    public final ActionController
            addAction = new ActionController(new DefaultJButton(Reference.getString("button.add")), menuBar.addEntry),
            editAction = new ActionController(new DefaultJButton(Reference.getString("button.edit")), menuBar.editEntry),
            removeAction = new ActionController(
                    new DefaultJButton(Reference.getString("button.remove")), menuBar.removeEntry),
            documentAction = new ActionController(
                    new DefaultJButton(Reference.getString("button.add_document")), menuBar.addDocument),
            companyNameAcion = new ActionController(null, menuBar.companyName),
            languageAction = new ActionController(null, menuBar.language),
            themeAction = new ActionController(null, menuBar.theme);
    public final StaticJTable dataTable;
    public final OnyxTableModel dataTableModel;

    public MainWindow(String title, DatabaseAccessor databaseAccessor) {
        super(true);
        setTitle(title);

        setJMenuBar(menuBar);

        dataTable = new StaticJTable();
        dataTableModel = new OnyxTableModel(databaseAccessor, dataTable, addAction, editAction, removeAction, documentAction);
        dataTableModel.addTableModelListener(e -> {
            dataTable.resizeColumnWidth(15, 300);
            if (((OnyxTableModel) dataTable.getModel()).getCurrentObjectsType() != ObjectType.CONTRACTOR)
                dataTable.alignColumn(3, JLabel.RIGHT);
        });

        navigation = new DefaultJTree(new DefaultMutableTreeNode(databaseAccessor.getCompanyName()));
        setupNavigation();

        final DefaultJPanel viewPanel = new DefaultJPanel();
        setupViewPanel(viewPanel);

        pack();
    }

    private void setupNavigation() {
        final JPanel navigationButtonsPanelWrapper = new JPanel();
        register(navigationButtonsPanelWrapper, Utils.gridPosition(0, 0));
        {
            final DefaultJPanel navigationButtonsPanel = new DefaultJPanel();
            navigationButtonsPanelWrapper.add(navigationButtonsPanel);
            {
                final DefaultMutableTreeNode root = navigation.getRoot();
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
                navigationButtonsPanel.register(new DefaultJScrollPane(navigation, 200, 507),
                                                Utils.gridPosition(0, 0));
            }
        }
    }

    private void setupViewPanel(DefaultJPanel viewPanel) {
        register(viewPanel, Utils.gridPosition(0, 1));
        {
            final JPanel viewPanelWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
            viewPanel.register(viewPanelWrapper, Utils.gridPosition(0, 0));
            {
                final DefaultJPanel editorButtonsPanel = new DefaultJPanel();
                viewPanelWrapper.add(editorButtonsPanel);
                {
                    addAction.setEnabled(false);
                    editorButtonsPanel.register(addAction.button, Utils.gridPosition(0, 0));
                    editAction.setEnabled(false);
                    editorButtonsPanel.register(editAction.button, Utils.gridPosition(0, 1));
                    removeAction.setEnabled(false);
                    editorButtonsPanel.register(removeAction.button, Utils.gridPosition(0, 2));
                    documentAction.setVisible(false);
                    editorButtonsPanel.register(documentAction.button, Utils.gridPosition(0, 3, 2, 1));
                }
            }
            viewPanel.register(new DefaultJScrollPane(dataTable, 700, 450),
                               Utils.gridPosition(1, 0));
            dataTableModel.setupTable();
        }
    }
}
