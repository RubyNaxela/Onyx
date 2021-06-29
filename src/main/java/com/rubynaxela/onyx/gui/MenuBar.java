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

    public final JMenuItem close, exit, addEntry, editEntry, removeEntry, associateDocument, companyName, language, theme;

    public MenuBar() {

        JMenu fileMenu = new JMenu(Reference.getString("menu.file")),
                editMenu = new JMenu(Reference.getString("menu.edit")),
                optionsMenu = new JMenu(Reference.getString("menu.options"));

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

        companyName = new JMenuItem(Reference.getString("menu.company_name"));
        optionsMenu.add(companyName);

        language = new JMenuItem(Reference.getString("menu.language"));
        optionsMenu.add(language);

        theme = new JMenuItem(Reference.getString("menu.theme"));
        optionsMenu.add(theme);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(optionsMenu);
    }
}
