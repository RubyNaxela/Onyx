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

package com.rubynaxela.onyx.gui.components;

import com.rubynaxela.onyx.util.Reference;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("FieldCanBeLocal")
public class DefaultJButton extends JButton {

    private final int fontSize = 13, fontStyle = Font.PLAIN;

    public DefaultJButton(String text) {
        super(text);
        setFont(Reference.getGlobalFont(fontSize, fontStyle));
    }
}

