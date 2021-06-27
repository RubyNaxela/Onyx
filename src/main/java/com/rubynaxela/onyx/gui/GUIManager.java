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

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.rubynaxela.onyx.Onyx;
import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.DatabaseController;
import com.rubynaxela.onyx.data.datatypes.auxiliary.LeafLabel;
import com.rubynaxela.onyx.data.datatypes.auxiliary.OnyxObjectsGroup;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.ClosedInvoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Contractor;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.OpenInvoice;
import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;
import com.rubynaxela.onyx.gui.dialogs.InputDialogsHandler;
import com.rubynaxela.onyx.gui.dialogs.MessageDialogsHandler;
import com.rubynaxela.onyx.io.IOHandler;
import com.rubynaxela.onyx.util.OsCheck;
import com.rubynaxela.onyx.util.Reference;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class GUIManager {

    private final MessageDialogsHandler messageDialogsHandler;
    private final IOHandler ioHandler;
    private final DatabaseAccessor databaseAccessor;
    private final DatabaseController databaseController;
    private InputDialogsHandler inputDialogsHandler;
    private MainWindow window;

    public GUIManager(Onyx instance) {
        this.messageDialogsHandler = instance.getMessageDialogsHandler();
        this.ioHandler = instance.getIOHandler();
        this.databaseAccessor = instance.getDatabaseAccessor();
        this.databaseController = instance.getDatabaseController();
    }

    @Contract(pure = true)
    @NotNull
    public static Font getGlobalFont(int size, int style) {
        return new Font("Verdana", style, size);
    }

    /**
     * Initial GUI settings. Sets the location of the navigation bar and other top bar options,
     * as well as the theme of the interface, then creates and initializes the main application window
     */
    @SuppressWarnings("unchecked")
    public void initMainWindow() {

        final ActionListener addButtonAction = e -> {
            if (window.dataTableModel.getCurrentObjects() == OnyxObjectsGroup.CONTRACTORS) {
                final Contractor contractor = inputDialogsHandler.showContractorDialog(null);
                if (contractor != null) {
                    databaseController.addEntry(contractor);
                    window.dataTableModel.display(OnyxObjectsGroup.CONTRACTORS);
                }
            } else if (window.dataTableModel.getCurrentObjects() == OnyxObjectsGroup.OPEN_INVOICES) {
                final Invoice invoice = inputDialogsHandler.showInvoiceDialog(null);
                if (invoice != null) {
                    databaseController.addEntry(invoice);
                    window.dataTableModel.display(OnyxObjectsGroup.OPEN_INVOICES);
                }
            } else if (window.dataTableModel.getCurrentObjects() == OnyxObjectsGroup.CLOSED_INVOICES) {
                final Invoice invoice = inputDialogsHandler.showInvoiceDialog(null);
                if (invoice != null) {
                    databaseController.addEntry(invoice);
                    window.dataTableModel.display(OnyxObjectsGroup.CLOSED_INVOICES);
                }
            }
        };
        final ActionListener editButtonAction = e -> {
            if (window.dataTableModel.getCurrentObjects() == OnyxObjectsGroup.CONTRACTORS) {
                final Contractor contractor = inputDialogsHandler.showContractorDialog(
                        (Contractor) window.dataTableModel.getCurrentObject());
                if (contractor != null) {
                    databaseController.editEntry(window.dataTableModel.getCurrentObject(), contractor);
                    window.dataTableModel.refresh();
                }
            } else if (window.dataTableModel.getCurrentObjects() == OnyxObjectsGroup.OPEN_INVOICES) {
                final Invoice invoice = inputDialogsHandler.showInvoiceDialog(
                        (OpenInvoice) window.dataTableModel.getCurrentObject());
                if (invoice != null) {
                    databaseController.editEntry(window.dataTableModel.getCurrentObject(), invoice);
                    window.dataTableModel.refresh();
                }
            } else if (window.dataTableModel.getCurrentObjects() == OnyxObjectsGroup.CLOSED_INVOICES) {
                final Invoice invoice = inputDialogsHandler.showInvoiceDialog(
                        (ClosedInvoice) window.dataTableModel.getCurrentObject());
                if (invoice != null) {
                    databaseController.editEntry(window.dataTableModel.getCurrentObject(), invoice);
                    window.dataTableModel.refresh();
                }
            }
        };
        final ActionListener removeButtonAction = e -> {
            if (messageDialogsHandler.askYesNoQuestion(
                    Reference.getString("message.action.confirm_remove"), false)) {
                databaseController.removeEntry(window.dataTableModel.getCurrentObject());
                window.dataTableModel.refresh();
            }
        };

        // Initial GUI settings
        initialSettings();

        // Loading the strings dictionary
        Reference.initDictionary(ioHandler, messageDialogsHandler);

        window = new MainWindow("Onyx", databaseAccessor);
        inputDialogsHandler = new InputDialogsHandler(window, databaseAccessor);

        window.setFileDropListener(event -> {
            try {
                if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    final List<File> droppedFiles = (List<File>) event.getTransferable()
                                                                      .getTransferData(DataFlavor.javaFileListFlavor);
                    event.dropComplete(true);
                    for (File file : droppedFiles) {
                        try {
                            final ImportedInvoice imported = Objects.requireNonNull(ioHandler.parseInvoice(file));
                            final Invoice invoice = inputDialogsHandler.showInvoiceDialog(Invoice.imported(imported));
                            if (invoice != null) {
                                databaseController.addEntry(invoice);
                                if (invoice instanceof OpenInvoice)
                                    window.dataTableModel.display(OnyxObjectsGroup.OPEN_INVOICES);
                                else window.dataTableModel.display(OnyxObjectsGroup.CLOSED_INVOICES);
                            }
                        } catch (Exception ex) {
                            messageDialogsHandler.showError(Reference.getFormatString("message.error.unrecognized_file",
                                                                                      file.getName()), false);
                            break;
                        }
                    }
                } else {
                    event.rejectDrop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        window.navigation.addTreeSelectionListener(e -> {
            final Object selectedNode = (
                    (DefaultMutableTreeNode) window.navigation.getLastSelectedPathComponent()).getUserObject();
            if (selectedNode instanceof LeafLabel) {

                final LeafLabel selectedNodeLabel = (LeafLabel) selectedNode;

                if (selectedNodeLabel.equals(MainWindow.contractorsLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.CONTRACTORS);
                else if (selectedNodeLabel.equals(MainWindow.openInvoicesLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.OPEN_INVOICES);
                else if (selectedNodeLabel.equals(MainWindow.closedInvoicesLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.CLOSED_INVOICES);
                else if (selectedNodeLabel.equals(MainWindow.claimsLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.CLAIMS);
                else if (selectedNodeLabel.equals(MainWindow.liabilitiesLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.LIABILITIES);
                else if (selectedNodeLabel.equals(MainWindow.contributionsLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.CONTRIBUTIONS);
                else if (selectedNodeLabel.equals(MainWindow.paymentsLabel))
                    window.dataTableModel.display(OnyxObjectsGroup.PAYMENTS);
            }
        });

        window.dataTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && ((JTable) mouseEvent.getSource()).getSelectedRow() != -1)
                    editButtonAction.actionPerformed(null);
            }
        });
        window.addButton.addActionListener(addButtonAction);
        window.editButton.addActionListener(editButtonAction);
        window.removeButton.addActionListener(removeButtonAction);
    }

    private void initialSettings() {

        // Platform-dependent utilization of the application top bar
        switch (OsCheck.getOperatingSystemType()) {
            case MAC_OS:
                System.setProperty("apple.awt.application.name", "Onyx");
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                break;
            case WINDOWS:
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                break;
        }

        // Look and feel settings
        try {
            if (!Reference.getProperty("theme").equals("dark")) {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } else {
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("CheckBox.icon.style", "filled");
    }
}
