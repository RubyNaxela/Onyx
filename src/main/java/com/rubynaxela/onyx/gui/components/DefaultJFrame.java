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
import java.util.function.Consumer;

public class DefaultJFrame extends JFrame {

    protected final ArrayList<Component> components;

    public DefaultJFrame(boolean exitOnClose) {
        components = new ArrayList<>();
        setVisible(true);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(exitOnClose ? EXIT_ON_CLOSE : DISPOSE_ON_CLOSE);
    }

    /**
     * This method is only used internally by the Java code and should not be used manually as it does not register
     * the components in the list. Please use the {@link DefaultJFrame#register(Component, Object)} method instead
     *
     * @param component   the component to be added
     * @param constraints an object expressing layout constraints for this component
     * @see DefaultJFrame#register
     */
    @Override
    @Deprecated
    public final void add(@NotNull Component component, Object constraints) {
        super.add(component, constraints);
    }

    /**
     * This method is a replacement for the {@link JFrame#add(Component, Object)} method. Appends the specified element
     * to the end of this list of components, then adds the specified component to the end of this container. Also
     * notifies the layout manager to add the component to this container's layout using the specified constraints object.
     * This method changes layout-related information, and therefore, invalidates the component hierarchy. If the container
     * has already been displayed, the hierarchy must be validated thereafter in order to display the added component.
     *
     * @param component   the component to be added
     * @param constraints an object expressing layout constraints for this component
     * @see JFrame#add(Component, Object)
     */
    public void register(@NotNull Component component, Object constraints) {
        this.components.add(component);
        super.add(component, constraints);
    }

    public ArrayList<Component> getFrameComponents() {
        return components;
    }

    public void forEachComponent(Consumer<Component> action) {
        components.forEach(action);
    }
}
