/*
 * Copyright (c) 2021 Jacek Pawelski a.k.a. RubyNaxela
 *
 * Licensed under the GNU General Public License v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * The license is included in file 'LICENSE.txt', which is part of this
 * source code package. You may also obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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

