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

import javax.swing.border.EmptyBorder;

public class BorderlessButton extends DefaultJButton {

    public BorderlessButton(String text) {
        super(text);
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
