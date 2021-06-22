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

package com.rubynaxela.onyx;

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.DatabaseController;
import com.rubynaxela.onyx.data.OnyxDatabase;
import com.rubynaxela.onyx.data.datatypes.Transaction;
import com.rubynaxela.onyx.data.datatypes.auxiliary.Monetary;
import com.rubynaxela.onyx.gui.GUIManager;
import com.rubynaxela.onyx.io.IOHandler;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public final class Onyx {

    private final OnyxDatabase database;
    private final IOHandler ioHandler;
    private final DatabaseController databaseController;
    private final DatabaseAccessor databaseAccessor;
    private final GUIManager guiManager;

    private Onyx() {

        // Initialize an empty database object
        database = new OnyxDatabase();

        // Load all application handlers, managers and controllers
        ioHandler = new IOHandler();
        databaseAccessor = new DatabaseAccessor(database);
        databaseController = new DatabaseController(this);
        guiManager = new GUIManager(this);

        // Initial GUI settings and loading the main window
        guiManager.initMainWindow();
        databaseController.loadDatabase();
    }

    /**
     * Application entry point
     *
     * @param args unused
     */
    public static void main(String[] args) {
//        new Onyx();
        Monetary amount1 = new Monetary(5, 93), amount2 = new Monetary(5, 8);
        amount1.add(amount2);
        System.out.println(amount1);
    }

    public OnyxDatabase getDatabase() {
        return database;
    }

    public IOHandler getIOHandler() {
        return ioHandler;
    }

    public DatabaseController getDatabaseController() {
        return databaseController;
    }

    public DatabaseAccessor getDatabaseAccessor() {
        return databaseAccessor;
    }

    static class Test {

        private int numberField;
        private Transaction[] textArray;

        public int getNumberField() {
            return numberField;
        }

        public void setNumberField(int numberField) {
            this.numberField = numberField;
        }

        public Transaction[] getTextArray() {
            return textArray;
        }

        public void setTextArray(Transaction[] textArray) {
            this.textArray = textArray;
        }
    }
}
