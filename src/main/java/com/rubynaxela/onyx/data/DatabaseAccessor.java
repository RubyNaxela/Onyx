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
            invoicesHeaders = new Vector<>(Arrays.asList(Reference.getString("label.invoice.id"),
                                                         Reference.getString("label.invoice.date"),
                                                         Reference.getString("label.invoice.contractor"),
                                                         Reference.getString("label.invoice.total"))),
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
        for (Contractor contractor : contractors)
            table.add(new ObjectRow(contractor.getUuid(),
                                    contractor.getName(),
                                    contractor.getDetails()));
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<Contractor> getContractorsVector() {
        return new Vector<>(database.getAllOfType(Contractor.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getOpenInvoicesTableVector() {
        final List<OpenInvoice> invoices = database.getAllOfType(OpenInvoice.class);
        invoices.sort(Comparator.comparing(Invoice::getDate)
                                .thenComparing(Invoice::getId).reversed());
        final Vector<ObjectRow> table = new Vector<>();
        for (OpenInvoice invoice : invoices)
            table.add(new ObjectRow(invoice.getUuid(),
                                    invoice.getId(),
                                    invoice.getDate(),
                                    database.get(invoice.getContractorUuid()).toString(),
                                    invoice.calculateAmount() + " PLN"));
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getClosedInvoicesTableVector() {
        final List<ClosedInvoice> invoices = database.getAllOfType(ClosedInvoice.class);
        invoices.sort(Comparator.comparing(Invoice::getDate)
                                .thenComparing(Invoice::getId).reversed());
        final Vector<ObjectRow> table = new Vector<>();
        for (ClosedInvoice invoice : invoices)
            table.add(new ObjectRow(invoice.getUuid(),
                                    invoice.getId(),
                                    invoice.getDate(),
                                    database.get(invoice.getContractorUuid()).toString(),
                                    invoice.calculateAmount() + " PLN"));
        return table;
    }

    private Vector<ObjectRow> getTransactionsTableVector(LinkedList<? extends Transaction> transactions) {
        transactions.sort(Comparator.comparing(Transaction::getDate)
                                    .thenComparing(Transaction::getDescription).reversed());
        final Vector<ObjectRow> table = new Vector<>();
        for (Transaction transaction : transactions)
            table.add(new ObjectRow(transaction.getUuid(),
                                    transaction.getDate(),
                                    database.get(transaction.getContractorUuid()).toString(),
                                    transaction.getDescription(),
                                    new Monetary(transaction.getAmount()) + " PLN"));
        return table;
    }

    private Vector<ObjectRow> getConsiderationsTableVector(LinkedList<? extends Consideration> considerations) {
        considerations.sort(Comparator.comparing(Consideration::getDate)
                                      .thenComparing(Consideration::getDescription).reversed());
        final Vector<ObjectRow> table = new Vector<>();
        for (Consideration consideration : considerations) {
            final ClosedInvoice invoice = (ClosedInvoice) getObject(consideration.getInvoiceUuid());
            table.add(new ObjectRow(consideration.getUuid(),
                                    consideration.getDate(),
                                    database.get(consideration.getContractorUuid()).toString(),
                                    consideration.getDescription(),
                                    new Monetary(consideration.getAmount()) + " PLN",
                                    PaymentMethod.get(invoice.getPaymentMethodUuid()).toString()));
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
        else if (type == ObjectType.OPEN_INVOICE) return new Table(invoicesHeaders, getOpenInvoicesTableVector());
        else if (type == ObjectType.CLOSED_INVOICE) return new Table(invoicesHeaders, getClosedInvoicesTableVector());
        else if (type == ObjectType.CLAIM) return new Table(transactionsHeaders, getClaimsTableVector());
        else if (type == ObjectType.LIABILITY) return new Table(transactionsHeaders, getLiabilitiesTableVector());
        else if (type == ObjectType.CONTRIBUTION) return new Table(considerationsHeaders, getContributionsTableVector());
        else if (type == ObjectType.PAYMENT) return new Table(considerationsHeaders, getPaymentsTableVector());
        else return null;
    }
}
