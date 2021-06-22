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

import com.rubynaxela.onyx.data.datatypes.*;
import com.rubynaxela.onyx.data.datatypes.auxiliary.ObjectRow;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public final class DatabaseAccessor {

    private final OnyxDatabase database;

    public DatabaseAccessor(OnyxDatabase database) {
        this.database = database;
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
        invoices.sort(Comparator.comparing(Invoice::getDate));
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
            operationData.add(operation.getAmount() + " PLN");
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
}
