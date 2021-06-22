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

package com.rubynaxela.onyx.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;
import com.rubynaxela.onyx.data.datatypes.raw.RawDatabase;

import java.io.File;
import java.io.IOException;

/**
 * The {@code FileIOHandler} class is responsible for handling JSON files - reading
 * from a file, deserialization and writing to directly from the database stored
 * by the program and serialization of data from the database and writing to a file
 *
 * @author Jacek Pawelski
 */
public final class IOHandler {

    private static final File DATA_FILE = new File("database.json");
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public RawDatabase parseDatabase() {
        try {
            return JSON_MAPPER.readValue(DATA_FILE, RawDatabase.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportDatabase(RawDatabase data) {
        try {
            JSON_MAPPER.writeValue(DATA_FILE, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImportedInvoice parseInvoice(File invoiceFile) throws IOException {
        return JSON_MAPPER.readValue(invoiceFile, ImportedInvoice.class);
    }
}