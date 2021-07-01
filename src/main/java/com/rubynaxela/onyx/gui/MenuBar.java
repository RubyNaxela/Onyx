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

    public final JMenuItem exit, addEntry, editEntry, removeEntry, addDocument, companyName, theme, languagePl, languageEnUs;

    public MenuBar() {

        JMenu fileMenu = new JMenu(Reference.getString("menu.file")),
                editMenu = new JMenu(Reference.getString("menu.edit")),
                optionsMenu = new JMenu(Reference.getString("menu.options")),
                languageMenu = new JMenu(Reference.getString("menu.language"));

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

        addDocument = new JMenuItem(Reference.getString("menu.add_document"));
        addDocument.setAccelerator(Reference.Shortcuts.DOCUMENT_STROKE);
        addDocument.setEnabled(false);
        editMenu.add(addDocument);

        exit = new JMenuItem(Reference.getString("menu.close"));
        exit.setAccelerator(Reference.Shortcuts.EXIT_STROKE);
        exit.addActionListener(e -> System.exit(0));
        fileMenu.add(exit);

        companyName = new JMenuItem(Reference.getString("menu.company_name"));
        optionsMenu.add(companyName);

        optionsMenu.add(languageMenu);

        languagePl = new JMenuItem(Reference.getString("menu.language.pl_PL"));
        languagePl.setEnabled(!Reference.getProperty("language").equals("pl_PL"));
        languageMenu.add(languagePl);

        languageEnUs = new JMenuItem(Reference.getString("menu.language.en_US"));
        languageEnUs.setEnabled(!Reference.getProperty("language").equals("en_US"));
        languageMenu.add(languageEnUs);

        theme = new JMenuItem(Reference.getString(Reference.getProperty("theme").equals("dark") ?
                                                  "menu.theme.light" : "menu.theme.dark"));
        optionsMenu.add(theme);

        this.add(fileMenu);
        this.add(editMenu);
        this.add(optionsMenu);
    }
}
