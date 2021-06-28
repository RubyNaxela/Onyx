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
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectType;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.*;
import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;
import com.rubynaxela.onyx.gui.dialogs.InputDialogsHandler;
import com.rubynaxela.onyx.gui.dialogs.MessageDialogsHandler;
import com.rubynaxela.onyx.io.IOHandler;
import com.rubynaxela.onyx.util.OsCheck;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.io.File;
import java.util.List;
import java.util.Objects;

import static com.rubynaxela.onyx.util.OsCheck.OSType.MAC_OS;

@SuppressWarnings("FieldCanBeLocal")
public final class GUIManager {

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

    /**
     * Initial GUI settings. Sets the location of the navigation bar and other top bar options,
     * as well as the theme of the interface, then creates and initializes the main application window
     */
    public void initMainWindow() {
        initialSettings();
        window = new MainWindow("Onyx", databaseAccessor);
        inputDialogsHandler = new InputDialogsHandler(window, databaseAccessor);
        setupListeners();
    }

    private void initialSettings() {

        final OsCheck.OSType os = OsCheck.getOperatingSystemType();

        switch (os) {
            case MAC_OS:
                System.setProperty("apple.awt.application.name", "Onyx");
                System.setProperty("apple.laf.useScreenMenuBar", "true");
                break;
            case WINDOWS:
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                break;
        }

        try {
            if (!Reference.getProperty("theme").equals("dark")) {
                if (os == MAC_OS) System.setProperty("apple.awt.application.appearance", "NSAppearanceNameAqua");
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } else {
                if (os == MAC_OS) System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
                UIManager.setLookAndFeel(new FlatDarculaLaf());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        UIManager.put("CheckBox.icon.style", "filled");
    }

    @SuppressWarnings("unchecked")
    private void setupListeners() {
        new DropTarget(window, (FileDropListener) event -> {
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
                                if (invoice instanceof OpenInvoice) window.dataTableModel.display(ObjectType.OPEN_INVOICE);
                                else window.dataTableModel.display(ObjectType.CLOSED_INVOICE);
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
            if (selectedNode instanceof LeafLabel)
                window.dataTableModel.display(((LeafLabel) selectedNode).getAssociatedGroup());
        });
        window.addButton.addActionListener(e -> {
            final Identifiable newObject = inputDialogsHandler.showObjectDialog(
                    window.dataTableModel.getCurrentObjectsType(), null);
            if (newObject != null) {
                databaseController.addEntry(newObject);
                window.dataTableModel.refresh();
            }
        });
        window.editButton.addActionListener(e -> {
            final Identifiable currentObject = window.dataTableModel.getCurrentObject();
            final Identifiable newValue = inputDialogsHandler.showObjectDialog(
                    window.dataTableModel.getCurrentObjectsType(), currentObject);
            if (newValue != null) {
                databaseController.editEntry(currentObject, newValue);
                window.dataTableModel.refresh();
            }
        });
        window.removeButton.addActionListener(e -> {
            if (messageDialogsHandler.askYesNoQuestion(
                    Reference.getString("message.action.confirm_remove"), false)) {
                databaseController.removeEntry(window.dataTableModel.getCurrentObject());
                window.dataTableModel.refresh();
            }
        });
        window.documentButton.addActionListener(e -> {
            final Identifiable currentObject = window.dataTableModel.getCurrentObject();
            if (currentObject instanceof Invoice) {
                final Operation operation;
                final double amount = ((Invoice) currentObject).calculateAmount().toDouble();
                if (currentObject instanceof OpenInvoice) {
                    if (amount > 0)
                        operation = inputDialogsHandler.showClaimDialog(new Claim((OpenInvoice) currentObject));
                    else if (amount < 0)
                        operation = inputDialogsHandler.showLiabilityDialog(new Liability((OpenInvoice) currentObject));
                    else operation = null;
                } else {
                    if (amount > 0)
                        operation = inputDialogsHandler.showContributionDialog(new Contribution((ClosedInvoice) currentObject));
                    else if (amount < 0)
                        operation = inputDialogsHandler.showPaymentDialog(new Payment((ClosedInvoice) currentObject));
                    else operation = null;
                }
                if (operation != null) {
                    databaseController.addEntry(operation);
                    if (currentObject instanceof ClosedInvoice)
                        ((ClosedInvoice) currentObject).setConsiderationUuid(operation.getUuid());
                    window.dataTableModel.refresh();
                }
            }
        });
        window.dataTable.addMouseListener((MousePressListener) event -> {
            if (event.getClickCount() == 2 && ((JTable) event.getSource()).getSelectedRow() != -1)
                window.editButton.doClick();
        });
    }
}
