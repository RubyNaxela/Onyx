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

package com.rubynaxela.onyx.data.datatypes.raw;

import com.rubynaxela.onyx.data.OnyxDatabase;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.*;
import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public class RawDatabase {

    private String companyName;
    private Contractor[] contractors;
    private OpenInvoice[] openInvoices;
    private ClosedInvoice[] closedInvoices;
    private Claim[] claims;
    private Liability[] liabilities;
    private Contribution[] contributions;
    private Payment[] payments;

    public RawDatabase() {
    }

    public RawDatabase(OnyxDatabase database) {
        this.contractors = database.getObjects(Contractor.class).toArray(new Contractor[0]);
        this.openInvoices = database.getObjects(OpenInvoice.class).toArray(new OpenInvoice[0]);
        this.closedInvoices = database.getObjects(ClosedInvoice.class).toArray(new ClosedInvoice[0]);
        this.claims = database.getObjects(Claim.class).toArray(new Claim[0]);
        this.liabilities = database.getObjects(Liability.class).toArray(new Liability[0]);
        this.contributions = database.getObjects(Contribution.class).toArray(new Contribution[0]);
        this.payments = database.getObjects(Payment.class).toArray(new Payment[0]);
    }

    @Contract(value = "-> new", pure = true)
    public static RawDatabase empty() {
        final RawDatabase emptyDatabase = new RawDatabase();
        emptyDatabase.companyName = "";
        emptyDatabase.contractors = new Contractor[0];
        emptyDatabase.openInvoices = new OpenInvoice[0];
        emptyDatabase.closedInvoices = new ClosedInvoice[0];
        emptyDatabase.claims = new Claim[0];
        emptyDatabase.liabilities = new Liability[0];
        emptyDatabase.contributions = new Contribution[0];
        emptyDatabase.payments = new Payment[0];
        return emptyDatabase;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Contractor[] getContractors() {
        return contractors;
    }

    public void setContractors(Contractor[] contractors) {
        this.contractors = contractors;
    }

    public OpenInvoice[] getOpenInvoices() {
        return openInvoices;
    }

    public void setOpenInvoices(OpenInvoice[] openInvoices) {
        this.openInvoices = openInvoices;
    }

    public ClosedInvoice[] getClosedInvoices() {
        return closedInvoices;
    }

    public void setClosedInvoices(ClosedInvoice[] closedInvoices) {
        this.closedInvoices = closedInvoices;
    }

    public Claim[] getClaims() {
        return claims;
    }

    public void setClaims(Claim[] claims) {
        this.claims = claims;
    }

    public Liability[] getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(Liability[] liabilities) {
        this.liabilities = liabilities;
    }

    public Contribution[] getContributions() {
        return contributions;
    }

    public void setContributions(Contribution[] contributions) {
        this.contributions = contributions;
    }

    public Payment[] getPayments() {
        return payments;
    }

    public void setPayments(Payment[] payments) {
        this.payments = payments;
    }
}
