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
            operationsHeaders = new Vector<>(Arrays.asList(Reference.getString("label.operation.date"),
                                                           Reference.getString("label.operation.contractor"),
                                                           Reference.getString("label.operation.description"),
                                                           Reference.getString("label.operation.amount")));

    private final OnyxDatabase database;

    public DatabaseAccessor(Onyx instance) {
        this.database = instance.getDatabase();
    }

    public String getCompanyName() {
        return database.getCompanyName();
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getContractorsTableVector() {
        final List<Contractor> contractors = database.getObjects(Contractor.class);
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
        return new Vector<>(database.getObjects(Contractor.class));
    }

    private Vector<ObjectRow> getInvoicesTableVector(LinkedList<? extends Invoice> invoices) {
        invoices.sort(Comparator.comparing(Invoice::getDate).thenComparing(Invoice::getId));
        final Vector<ObjectRow> table = new Vector<>();
        for (Invoice invoice : invoices) {
            ObjectRow invoiceData = new ObjectRow(invoice);
            invoiceData.add(invoice.getId());
            invoiceData.add(invoice.getDate());
            invoiceData.add(database.getObject(invoice.getContractorUuid()).toString());
            invoiceData.add(invoice.calculateAmount() + " PLN");
            table.add(invoiceData);
        }
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getOpenInvoicesTableVector() {
        return getInvoicesTableVector(database.getObjects(OpenInvoice.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getClosedInvoicesTableVector() {
        return getInvoicesTableVector(database.getObjects(ClosedInvoice.class));
    }

    private Vector<ObjectRow> getOperationsTableVector(LinkedList<? extends Operation> operations) {
        operations.sort(Comparator.comparing(Operation::getDate));
        final Vector<ObjectRow> table = new Vector<>();
        for (Operation operation : operations) {
            ObjectRow operationData = new ObjectRow(operation);
            operationData.add(operation.getDate());
            operationData.add(database.getObject(operation.getContractorUuid()).toString());
            operationData.add(operation.getDescription());
            operationData.add(new Monetary(operation.getAmount()) + " PLN");
            table.add(operationData);
        }
        return table;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getClaimsTableVector() {
        return getOperationsTableVector(database.getObjects(Claim.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getLiabilitiesTableVector() {
        return getOperationsTableVector(database.getObjects(Liability.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getContributionsTableVector() {
        return getOperationsTableVector(database.getObjects(Contribution.class));
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getPaymentsTableVector() {
        return getOperationsTableVector(database.getObjects(Payment.class));
    }

    public Identifiable getObject(@Nullable String uuid) {
        return (Identifiable) database.getObjects().stream().filter(
                c -> ((Identifiable) c).getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @Contract(value = "!null -> new", pure = true)
    public Table getTable(ObjectType type) {
        if (type == ObjectType.CONTRACTOR) return new Table(contractorsHeaders, getContractorsTableVector());
        else if (type == ObjectType.OPEN_INVOICE) return new Table(invoicesHeaders, getOpenInvoicesTableVector());
        else if (type == ObjectType.CLOSED_INVOICE) return new Table(invoicesHeaders, getClosedInvoicesTableVector());
        else if (type == ObjectType.CLAIM) return new Table(operationsHeaders, getClaimsTableVector());
        else if (type == ObjectType.LIABILITY) return new Table(operationsHeaders, getLiabilitiesTableVector());
        else if (type == ObjectType.CONTRIBUTION) return new Table(operationsHeaders, getContributionsTableVector());
        else if (type == ObjectType.PAYMENT) return new Table(operationsHeaders, getPaymentsTableVector());
        else return null;
    }
}
