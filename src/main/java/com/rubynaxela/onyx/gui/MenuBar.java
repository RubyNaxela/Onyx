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

package com.rubynaxela.onyx.gui;

import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;

@SuppressWarnings("FieldCanBeLocal")
public class MenuBar extends JMenuBar {

    public final JMenuItem addEntry, editEntry, removeEntry, associateDocument, close, exit;

    public MenuBar() {

        JMenu fileMenu = new JMenu(Reference.getString("menu.file")),
                editMenu = new JMenu(Reference.getString("menu.edit"));

        addEntry = new JMenuItem(Reference.getString("menu.entry.add"));
        addEntry.setAccelerator(Reference.Shortcuts.NEW_STROKE);
        addEntry.setEnabled(false);
        editMenu.add(addEntry);

        editEntry = new JMenuItem(Reference.getString("menu.entry.edit"));
        editEntry.setAccelerator(Reference.Shortcuts.EDIT_STROKE);
        editEntry.setEnabled(false);
        editMenu.add(editEntry);

        removeEntry = new JMenuItem(Reference.getString("menu.entry.remove"));
        removeEntry.setAccelerator(Reference.Shortcuts.REMOVE_STROKE);
        removeEntry.setEnabled(false);
        editMenu.add(removeEntry);

        associateDocument = new JMenuItem(Reference.getString("menu.associate_document"));
        associateDocument.setAccelerator(Reference.Shortcuts.DOCUMENT_STROKE);
        associateDocument.setEnabled(false);
        editMenu.add(associateDocument);

        close = new JMenuItem(Reference.getString("menu.close"));
        close.setAccelerator(Reference.Shortcuts.CLOSE_STROKE);
        fileMenu.add(close);

        exit = new JMenuItem(Reference.getString("menu.exit"));
        exit.setAccelerator(Reference.Shortcuts.EXIT_STROKE);
        fileMenu.add(exit);

        this.add(fileMenu);
        this.add(editMenu);
    }
}
