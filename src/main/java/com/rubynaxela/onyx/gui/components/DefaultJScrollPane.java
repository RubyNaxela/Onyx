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

import javax.swing.*;
import java.awt.*;

/**
 * The {@code DefaultScrollPane} constructor serves as a shortened syntax for creating
 * a new {@link JScrollPane} with fixed dimensions
 *
 * @author Jacek Pawelski
 */
public final class DefaultJScrollPane extends JScrollPane {

    private final int paneWidth, paneHeight;

    public DefaultJScrollPane(JComponent component, int paneWidth, int paneHeight) {
        super(component);
        this.paneWidth = paneWidth;
        this.paneHeight = paneHeight;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(paneWidth, paneHeight);
    }
}
