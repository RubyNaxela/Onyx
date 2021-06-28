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

package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.Onyx;
import com.rubynaxela.onyx.data.datatypes.auxiliary.Monetary;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectType;
import com.rubynaxela.onyx.data.datatypes.auxiliary.PaymentMethod;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.*;
import com.rubynaxela.onyx.gui.Table;
import com.rubynaxela.onyx.util.Reference;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class DatabaseAccessor {

    private static final Vector<String>
            contractorsHeaders = new Vector<>(Arrays.asList(Reference.getString("label.contractor.name"),
                                                            Reference.getString("label.contractor.details"))),
            openInvoicesHeaders = new Vector<>(Arrays.asList(Reference.getString("label.invoice.id"),
                                                             Reference.getString("label.invoice.date"),
                                                             Reference.getString("label.invoice.contractor"),
                                                             Reference.getString("label.invoice.total"))),
            closedInvoicesHeaders = new Vector<>(Arrays.asList(Reference.getString("label.invoice.id"),
                                                               Reference.getString("label.invoice.date"),
                                                               Reference.getString("label.invoice.contractor"),
                                                               Reference.getString("label.invoice.total"),
                                                               Reference.getString("label.invoice.operation"))),
            transactionsHeaders = new Vector<>(Arrays.asList(Reference.getString("label.operation.date"),
                                                             Reference.getString("label.operation.contractor"),
                                                             Reference.getString("label.operation.description"),
                                                             Reference.getString("label.operation.amount"))),
            considerationsHeaders = new Vector<>(Arrays.asList(Reference.getString("label.operation.date"),
                                                               Reference.getString("label.operation.contractor"),
                                                               Reference.getString("label.operation.description"),
                                                               Reference.getString("label.operation.amount"),
                                                               Reference.getString("label.operation.payment_method")));

    private final OnyxDatabase database;

    public DatabaseAccessor(Onyx instance) {
        this.database = instance.getDatabase();
    }

    public String getCompanyName() {
        return database.getCompanyName();
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getContractorsTableVector() {
        final List<Contractor> contractors = database.getAllOfType(Contractor.class);
        contractors.sort(Comparator.comparing(Contractor::getName));
        final Vector<ObjectRow> table = new Vector<>();
        for (Contractor contractor : contractors) {
            ObjectRow contractorData = new ObjectRow(contractor);
            contractorData.add(contractor.getName());
            contractorData.add(contractor.getDetails());
            table.add(contractorData);
        }
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<Contractor> getContractorsVector() {
        return new Vector<>(database.getAllOfType(Contractor.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getOpenInvoicesTableVector() {
        final List<OpenInvoice> invoices = database.getAllOfType(OpenInvoice.class);
        invoices.sort(Comparator.comparing(Invoice::getDate).thenComparing(Invoice::getId));
        final Vector<ObjectRow> table = new Vector<>();
        for (OpenInvoice invoice : invoices) {
            ObjectRow invoiceData = new ObjectRow(invoice);
            invoiceData.add(invoice.getId());
            invoiceData.add(invoice.getDate());
            invoiceData.add(database.get(invoice.getContractorUuid()).toString());
            invoiceData.add(invoice.calculateAmount() + " PLN");
            table.add(invoiceData);
        }
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getClosedInvoicesTableVector() {
        final List<ClosedInvoice> invoices = database.getAllOfType(ClosedInvoice.class);
        invoices.sort(Comparator.comparing(Invoice::getDate).thenComparing(Invoice::getId));
        final Vector<ObjectRow> table = new Vector<>();
        for (ClosedInvoice invoice : invoices) {
            ObjectRow invoiceData = new ObjectRow(invoice);
            invoiceData.add(invoice.getId());
            invoiceData.add(invoice.getDate());
            invoiceData.add(database.get(invoice.getContractorUuid()).toString());
            invoiceData.add(invoice.calculateAmount() + " PLN");
            final Consideration consideration = (Consideration) getObject(invoice.getConsiderationUuid());
            invoiceData.add(consideration != null ? consideration.getDescription() : "-");
            table.add(invoiceData);
        }
        return table;
    }

    private Vector<ObjectRow> getTransactionsTableVector(LinkedList<? extends Transaction> transactions) {
        transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getDescription));
        final Vector<ObjectRow> table = new Vector<>();
        for (Transaction transaction : transactions) {
            ObjectRow operationData = new ObjectRow(transaction);
            operationData.add(transaction.getDate());
            operationData.add(database.get(transaction.getContractorUuid()).toString());
            operationData.add(transaction.getDescription());
            operationData.add(new Monetary(transaction.getAmount()) + " PLN");
            table.add(operationData);
        }
        return table;
    }

    private Vector<ObjectRow> getConsiderationsTableVector(LinkedList<? extends Consideration> considerations) {
        considerations.sort(Comparator.comparing(Consideration::getDate).thenComparing(Consideration::getDescription));
        final Vector<ObjectRow> table = new Vector<>();
        for (Consideration consideration : considerations) {
            ObjectRow operationData = new ObjectRow(consideration);
            operationData.add(consideration.getDate());
            operationData.add(database.get(consideration.getContractorUuid()).toString());
            operationData.add(consideration.getDescription());
            operationData.add(new Monetary(consideration.getAmount()) + " PLN");
            operationData.add(PaymentMethod.get(((ClosedInvoice) getObject(
                    consideration.getInvoiceUuid())).getPaymentMethodUuid()).toString());
            table.add(operationData);
        }
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getClaimsTableVector() {
        return getTransactionsTableVector(database.getAllOfType(Claim.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getLiabilitiesTableVector() {
        return getTransactionsTableVector(database.getAllOfType(Liability.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getContributionsTableVector() {
        return getConsiderationsTableVector(database.getAllOfType(Contribution.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getPaymentsTableVector() {
        return getConsiderationsTableVector(database.getAllOfType(Payment.class));
    }

    public Identifiable getObject(@Nullable String uuid) {
        return (Identifiable) database.getAll().stream().filter(
                c -> ((Identifiable) c).getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @Contract(value = "!null -> new", pure = true)
    public Table getTable(ObjectType type) {
        if (type == ObjectType.CONTRACTOR) return new Table(contractorsHeaders, getContractorsTableVector());
        else if (type == ObjectType.OPEN_INVOICE) return new Table(openInvoicesHeaders, getOpenInvoicesTableVector());
        else if (type == ObjectType.CLOSED_INVOICE) return new Table(closedInvoicesHeaders, getClosedInvoicesTableVector());
        else if (type == ObjectType.CLAIM) return new Table(transactionsHeaders, getClaimsTableVector());
        else if (type == ObjectType.LIABILITY) return new Table(transactionsHeaders, getLiabilitiesTableVector());
        else if (type == ObjectType.CONTRIBUTION) return new Table(considerationsHeaders, getContributionsTableVector());
        else if (type == ObjectType.PAYMENT) return new Table(considerationsHeaders, getPaymentsTableVector());
        else return null;
    }
}
