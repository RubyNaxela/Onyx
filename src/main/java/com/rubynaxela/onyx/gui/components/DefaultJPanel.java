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

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DefaultJPanel extends JPanel {

    protected final ArrayList<Component> components;

    public DefaultJPanel() {
        components = new ArrayList<>();
        setLayout(new GridBagLayout());
    }

    @Override
    @Deprecated
    public final void add(@NotNull Component component, Object constraints) {
        super.add(component, constraints);
    }

    public void register(@NotNull Component component, Object constraints) {
        this.components.add(component);
        super.add(component, constraints);
    }
}
