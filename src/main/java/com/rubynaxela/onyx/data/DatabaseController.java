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
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Identifiable;
import com.rubynaxela.onyx.data.datatypes.databaseobjects.Referring;
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

    @SuppressWarnings("unchecked")
    public void loadDatabase() {
        final RawDatabase data = Objects.requireNonNull(ioHandler.parseDatabase());
        database.init(data.getCompanyName(),
                      Arrays.asList(data.getContractors()),
                      Arrays.asList(data.getOpenInvoices()),
                      Arrays.asList(data.getClosedInvoices()),
                      Arrays.asList(data.getClaims()),
                      Arrays.asList(data.getLiabilities()),
                      Arrays.asList(data.getContributions()),
                      Arrays.asList(data.getPayments()));
    }

    private void saveChanges() {
        ioHandler.exportDatabase(new RawDatabase(database));
    }

    public void setCompanyName(String newName) {
        database.setCompanyName(newName);
        saveChanges();
    }

    public void addEntry(Identifiable entry) {
        database.getAll().add(entry);
        saveChanges();
    }

    public void editEntry(Identifiable entry, Identifiable newValue) {
        database.getAll().remove(Objects.requireNonNull(database.get(entry.getUuid())));
        database.getAll().add(newValue);
        saveChanges();
    }

    public void removeEntry(Identifiable entry) {
        database.getAll().remove(Objects.requireNonNull(database.get(entry.getUuid())));
        removeEmptyReferences();
        saveChanges();
    }

    private void removeEmptyReferences() {
        for (Referring object : database.getAllOfType(Referring.class))
            if (object.getReference() != null && !database.contains(object.getReference())) object.removeReference();
    }
}