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

import com.rubynaxela.onyx.gui.GUIManager;
import org.intellij.lang.annotations.MagicConstant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DefaultJButton extends JButton {

    private int fontSize = 13, fontStyle = Font.PLAIN;

    /**
     * Creates a button with no set text or icon
     *
     * @see JButton#JButton()
     */
    public DefaultJButton() {
        super();
    }

    /**
     * Creates a button with text
     *
     * @param text the text of the button
     * @see JButton#JButton(String)
     */
    public DefaultJButton(String text) {
        super(text);
        updateFont();
    }

    private void updateFont() {
        setFont(GUIManager.getGlobalFont(fontSize, fontStyle));
    }

    /**
     * Sets the button text and assigns an action with {@link java.awt.event.ActionListener}
     *
     * @param text   the text of the button
     * @param action the action of the button
     */
    public void init(String text, ActionListener action) {
        setText(text);
        addActionListener(action);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int size) {
        fontSize = size;
        updateFont();
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(@MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style) {
        fontStyle = style;
        updateFont();
    }

    public void setFontSizeAndStyle(int size,
                                    @MagicConstant(flags = {Font.PLAIN, Font.BOLD, Font.ITALIC}) int style) {
        fontSize = size;
        fontStyle = style;
        updateFont();
    }
}

