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

import java.util.LinkedList;
import java.util.Vector;

public final class DatabaseAccessor {

    private final OnyxDatabase database;

    public DatabaseAccessor(OnyxDatabase database) {
        this.database = database;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<ObjectRow> getContractorsTableVector() {
        Vector<ObjectRow> data = new Vector<>();
        for (Contractor contractor : database.getObjects(Contractor.class)) {
            ObjectRow contractorData = new ObjectRow(contractor);
            contractorData.add(contractor.getName());
            contractorData.add(contractor.getDetails());
            data.add(contractorData);
        }
        return data;
    }

    @Contract(value = "-> new", pure = true)
    public Vector<Contractor> getContractorsVector() {
        return new Vector<>(database.getObjects(Contractor.class));
    }

    private Vector<ObjectRow> getInvoicesTableVector(LinkedList<? extends Invoice> invoices) {
        Vector<ObjectRow> data = new Vector<>();
        for (Invoice invoice : invoices) {
            ObjectRow invoiceData = new ObjectRow(invoice);
            invoiceData.add(invoice.getId());
            invoiceData.add(invoice.getDate());
            invoiceData.add(((Contractor) database.getObject(invoice.getContractorUuid())).getName());
            invoiceData.add(invoice.calculateAmount() + " PLN");
            data.add(invoiceData);
        }
        return data;
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
        Vector<ObjectRow> data = new Vector<>();
        for (Operation operation : operations) {
            ObjectRow operationData = new ObjectRow(operation);
            operationData.add(operation.getUuid());
            operationData.add(operation.getContractorUuid());
            operationData.add(operation.getAmount() + " PLN");
            data.add(operationData);
        }
        return data;
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

    public Identifiable getObject(String uuid) {
        return (Identifiable) database.getObjects().stream().filter(
                c -> ((Identifiable) c).getUuid().equals(uuid)).findFirst().orElse(null);
    }
}
