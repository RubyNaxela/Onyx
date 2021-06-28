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

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;

public interface FileDropListener extends DropTargetListener {

    @Override
    default void dragEnter(DropTargetDragEvent e) {
    }

    @Override
    default void dragOver(DropTargetDragEvent e) {
    }

    @Override
    default void dropActionChanged(DropTargetDragEvent e) {
    }

    @Override
    default void dragExit(DropTargetEvent e) {
    }
}
