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

@SuppressWarnings("FieldCanBeLocal")
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
        Reference.loadProperties(messageDialogsHandler);
        Reference.loadDictionary(ioHandler, messageDialogsHandler);
        databaseAccessor = new DatabaseAccessor(this);
        databaseController = new DatabaseController(this);
        guiManager = new GUIManager(this);

        databaseController.loadDatabase();
        guiManager.initMainWindow();
    }

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
}
