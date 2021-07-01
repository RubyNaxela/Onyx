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

import com.rubynaxela.onyx.data.datatypes.databaseobjects.ClosedInvoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.InvoiceItem;
import com.rubynaxela.onyx.gui.components.StaticJTable;
import com.rubynaxela.onyx.gui.dialogs.InputDialogsHandler;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;

public final class InvoiceTableModel extends DefaultTableModel {

    public static Vector<String> invoiceItemsTableHeaders = new Vector<>(
            Arrays.asList(Reference.getString("label.invoice_item.date"),
                          Reference.getString("label.invoice_item.source"),
                          Reference.getString("label.invoice_item.description"),
                          Reference.getString("label.invoice_item.net_unit_price"),
                          Reference.getString("label.invoice_item.quantity"),
                          Reference.getString("label.invoice_item.net_total"),
                          Reference.getString("label.invoice_item.tax"),
                          Reference.getString("label.invoice_item.total")));
    private final ActionController addAction, editAction, removeAction;

    private final StaticJTable ownerTable;
    private final InputDialogsHandler inputDialogsHandler;
    private final boolean editsEnabled;

    private final List<InvoiceItem> items;
    private InvoiceItem currentItem;

    public InvoiceTableModel(Invoice invoice, StaticJTable ownerTable, ActionController addAction, ActionController editAction,
                             ActionController removeAction, InputDialogsHandler inputDialogsHandler, boolean imported) {
        super();
        this.items = invoice != null ? new LinkedList<>(Arrays.asList(invoice.getItems())) : new LinkedList<>();
        this.ownerTable = ownerTable;
        this.addAction = addAction;
        this.editAction = editAction;
        this.removeAction = removeAction;
        this.inputDialogsHandler = inputDialogsHandler;
        this.editsEnabled = !(invoice instanceof ClosedInvoice) || imported;
        setupListeners();
        setupTable();
        refresh();
    }

    public void setupTable() {
        ownerTable.setModel(this);
        ownerTable.getSelectionModel().addListSelectionListener(e -> {
            final int rowIndex = ownerTable.getSelectedRow();
            if (editsEnabled && rowIndex >= 0) {
                currentItem = items.get(rowIndex);
                editAction.setEnabled(true);
                removeAction.setEnabled(true);
            }
        });
        ownerTable.resizeColumnWidth(15, 300);
    }

    public void refresh() {
        items.sort(Comparator.comparing(InvoiceItem::getDate)
                             .thenComparing(InvoiceItem::getSource)
                             .thenComparing(InvoiceItem::getDescription)
                             .thenComparing(InvoiceItem::getRate));
        setDataVector(Invoice.getItemsTableVector(items), invoiceItemsTableHeaders);
        ownerTable.alignColumn(3, JLabel.RIGHT);
        ownerTable.alignColumn(4, JLabel.RIGHT);
        ownerTable.alignColumn(5, JLabel.RIGHT);
        ownerTable.alignColumn(6, JLabel.RIGHT);
        ownerTable.alignColumn(7, JLabel.RIGHT);
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    private void setupListeners() {
        addAction.setListener(e -> {
            final InvoiceItem newItem = inputDialogsHandler.showInvoiceItemDialog(null);
            if (newItem != null) {
                items.add(newItem);
                refresh();
            }
        });
        editAction.setListener(e -> {
            final InvoiceItem newItem = inputDialogsHandler.showInvoiceItemDialog(currentItem);
            if (newItem != null) {
                items.remove(currentItem);
                items.add(newItem);
                refresh();
            }
        });
        removeAction.setListener(e -> {
            if (inputDialogsHandler.askYesNoQuestion(
                    Reference.getString("message.action.confirm_remove"), false)) {
                items.remove(currentItem);
                refresh();
            }
        });
        ownerTable.addMouseListener((MousePressListener) event -> {
            if (event.getClickCount() == 2 && ((JTable) event.getSource()).getSelectedRow() != -1)
                editAction.perform();
        });
    }
}
