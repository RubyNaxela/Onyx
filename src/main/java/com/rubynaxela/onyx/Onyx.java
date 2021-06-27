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
import com.rubynaxela.onyx.gui.GUIManager;
import com.rubynaxela.onyx.gui.dialogs.MessageDialogsHandler;
import com.rubynaxela.onyx.io.IOHandler;
import com.rubynaxela.onyx.util.Reference;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public final class Onyx {

    private final OnyxDatabase database;
    private final MessageDialogsHandler messageDialogsHandler;
    private final IOHandler ioHandler;
    private final DatabaseController databaseController;
    private final DatabaseAccessor databaseAccessor;
    private final GUIManager guiManager;

    private Onyx() {

        database = new OnyxDatabase();

        messageDialogsHandler = new MessageDialogsHandler(null);
        ioHandler = new IOHandler();
        databaseAccessor = new DatabaseAccessor(database);
        databaseController = new DatabaseController(this);
        guiManager = new GUIManager(this);

        Reference.loadProperties(messageDialogsHandler);
        databaseController.loadDatabase();
        guiManager.initMainWindow();
    }

    /**
     * Application entry point
     *
     * @param args unused
     */
    public static void main(String[] args) {
        new Onyx();
    }

    public OnyxDatabase getDatabase() {
        return database;
    }

    public MessageDialogsHandler getMessageDialogsHandler() {
        return messageDialogsHandler;
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

    public GUIManager getGuiManager() {
        return guiManager;
    }
}
