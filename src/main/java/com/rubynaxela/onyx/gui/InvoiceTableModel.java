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

import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.ClosedInvoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Invoice;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.InvoiceItem;
import com.rubynaxela.onyx.gui.components.StaticJTable;
import com.rubynaxela.onyx.gui.dialogs.InputDialogsHandler;
import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

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
    private final ActionController addButton, editButton, removeButton;

    private final StaticJTable ownerTable;
    private final InputDialogsHandler inputDialogsHandler;
    private final boolean editsEnabled;

    private final List<InvoiceItem> items;
    private InvoiceItem currentItem;

    public InvoiceTableModel(Invoice invoice, StaticJTable ownerTable, ActionController addButton, ActionController editButton,
                             ActionController removeButton, InputDialogsHandler inputDialogsHandler, boolean imported) {
        super();
        this.items = invoice != null ? new LinkedList<>(Arrays.asList(invoice.getItems())) : new LinkedList<>();
        this.ownerTable = ownerTable;
        this.addButton = addButton;
        this.editButton = editButton;
        this.removeButton = removeButton;
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
                editButton.setEnabled(true);
                removeButton.setEnabled(true);
            }
        });
        ownerTable.resizeColumnWidth(15, 300);
    }

    public void refresh() {
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
        addButton.setListener(e -> System.out.println("Dodawanie przedmiotu is here"));
        editButton.setListener(e -> System.out.println("Edycja przedmiotu is here"));
        removeButton.setListener(e -> {
            if (inputDialogsHandler.askYesNoQuestion(
                    Reference.getString("message.action.confirm_remove"), false)) {
                items.remove(currentItem);
                refresh();
            }
        });
    }
}
