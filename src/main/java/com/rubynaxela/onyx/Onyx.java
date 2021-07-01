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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

@SuppressWarnings("FieldCanBeLocal")
public final class Onyx {

    private static Onyx currentInstance;

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
        currentInstance = new Onyx();
    }

    public static void restart() {
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            final File currentJar = new File(Onyx.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (!currentJar.getName().endsWith(".jar")) {
                currentInstance.messageDialogsHandler.showInfo(Reference.getString("message.info.app_needs_restart"));
                return;
            }
            new ProcessBuilder(Arrays.asList(javaBin, "-jar", currentJar.getPath())).start();
            System.exit(0);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
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
