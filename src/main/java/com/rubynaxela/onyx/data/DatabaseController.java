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
import com.rubynaxela.onyx.data.datatypes.Identifiable;
import com.rubynaxela.onyx.data.datatypes.Invoice;
import com.rubynaxela.onyx.data.datatypes.raw.RawDatabase;
import com.rubynaxela.onyx.io.IOHandler;

import java.util.Arrays;
import java.util.Objects;

public final class DatabaseController {

    private final OnyxDatabase database;
    private final IOHandler ioHandler;

    public DatabaseController(Onyx instance) {
        this.database = instance.getDatabase();
        this.ioHandler = instance.getIOHandler();
    }

    public void loadDatabase() {
        final RawDatabase data = Objects.requireNonNull(ioHandler.parseDatabase());
        database.getObjects().addAll(Arrays.asList(data.getContractors()));
        database.getObjects().addAll(Arrays.asList(data.getOpenInvoices()));
        database.getObjects().addAll(Arrays.asList(data.getClosedInvoices()));
        database.getObjects().addAll(Arrays.asList(data.getClaims()));
        database.getObjects().addAll(Arrays.asList(data.getLiabilities()));
        database.getObjects().addAll(Arrays.asList(data.getContributions()));
        database.getObjects().addAll(Arrays.asList(data.getPayments()));
    }

    private void saveChanges() {
        ioHandler.exportDatabase(new RawDatabase(database));
    }

    public void addEntry(Identifiable entry) {
        database.getObjects().add(entry);
        saveChanges();
    }

    public void removeEntry(Identifiable entry) {
        database.getObjects().remove(Objects.requireNonNull(database.getObject(entry.getUuid())));
        saveChanges();
    }

    public void editEntry(Identifiable entry, Identifiable newValue) {
        database.getObjects().remove(Objects.requireNonNull(database.getObject(entry.getUuid())));
        database.getObjects().add(newValue);
        saveChanges();
    }
}