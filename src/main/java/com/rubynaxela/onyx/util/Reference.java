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

package com.rubynaxela.onyx.util;

import com.formdev.flatlaf.icons.*;
import com.rubynaxela.onyx.gui.dialogs.MessageDialogsHandler;
import com.rubynaxela.onyx.io.IOHandler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.META_DOWN_MASK;
import static java.awt.event.KeyEvent.*;

public final class Reference {

    private static final Properties properties = new Properties();
    private static final HashMap<String, String> primaryDictionary = new HashMap<>(), backupDictionary = new HashMap<>();

    public static void loadProperties(MessageDialogsHandler messageDialogsHandler) {
        try {
            properties.load(new FileReader(".properties"));
        } catch (IOException e) {
            messageDialogsHandler.showError("Could not read the properties file. Using default settings.", false);
            try {
                properties.put("language", "en_US");
                properties.put("theme", "light");
                properties.store(new FileWriter(".properties"), "");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getProperty(@NotNull String key) {
        return properties.containsKey(key) ? properties.getProperty(key) : "property." + key;
    }

    public static void setProperty(@NotNull String key, @NotNull String value) {
        try {
            properties.put(key, value);
            properties.store(new FileWriter(".properties"), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDictionary(IOHandler ioHandler, MessageDialogsHandler messageDialogsHandler) {
        final Map<String, String> _primaryDictionary = ioHandler.parseLanguageFile(getProperty("language"));
        final Map<String, String> _backupDictionary = ioHandler.parseLanguageFile("en_US");
        if (_primaryDictionary == null && _backupDictionary == null)
            messageDialogsHandler.showError("Could not read any language file. The program will now exit.", true);
        if (_primaryDictionary != null) primaryDictionary.putAll(_primaryDictionary);
        if (_backupDictionary != null) backupDictionary.putAll(_backupDictionary);
    }

    @NotNull
    public static String getString(@NotNull String key) {
        if (primaryDictionary.containsKey(key))
            return Objects.requireNonNull(primaryDictionary.get(key));
        else if (backupDictionary.containsKey(key))
            return Objects.requireNonNull(backupDictionary.get(key));
        else
            return key;
    }

    @NotNull
    public static String getFormatString(@NotNull String key, Object... args) {
        if (primaryDictionary.containsKey(key))
            return String.format(Objects.requireNonNull(primaryDictionary.get(key)), args);
        else if (backupDictionary.containsKey(key))
            return String.format(Objects.requireNonNull(backupDictionary.get(key)), args);
        else
            return key;
    }

    @NotNull
    public static String getString(@NotNull String key, @NotNull String fallbackValue) {
        return (primaryDictionary.containsKey(key) || backupDictionary.containsKey(key)) ? getString(key) : fallbackValue;
    }

    @Contract(pure = true)
    @NotNull
    public static Font getGlobalFont(int size, int style) {
        return new Font("Verdana", style, size);
    }

    @Nullable
    public static Icon getIcon(@NotNull String key) {
        return switch (key) {
            case "dialog.info" -> new FlatOptionPaneInformationIcon();
            case "dialog.warning" -> new FlatOptionPaneWarningIcon();
            case "dialog.error" -> new FlatOptionPaneErrorIcon();
            case "dialog.question" -> new FlatOptionPaneQuestionIcon();
            case "dialog.data" -> new FlatOptionPaneAbstractIcon("Actions.Green", "Actions.Green") {
                @Override
                protected Shape createOutside() {
                    return new Ellipse2D.Float(2, 2, 28, 28);
                }

                @Override
                protected Shape createInside() {
                    Path2D q = new Path2D.Float(Path2D.WIND_EVEN_ODD);
                    q.append(new Rectangle2D.Float(8, 8, 6, 16), false);
                    q.append(new Rectangle2D.Float(18, 8, 6, 6), false);
                    q.append(new Rectangle2D.Float(18, 18, 6, 6), false);
                    return q;
                }
            };
            default -> null;
        };
    }

    public static final class Shortcuts {

        private static final boolean isMacOS = OsCheck.getOperatingSystemType() == OsCheck.OSType.MAC_OS;
        public static final KeyStroke
                NEW_STROKE = KeyStroke.getKeyStroke(VK_N, !isMacOS ? CTRL_DOWN_MASK : META_DOWN_MASK),
                EDIT_STROKE = KeyStroke.getKeyStroke(VK_ENTER, !isMacOS ? CTRL_DOWN_MASK : META_DOWN_MASK),
                REMOVE_STROKE = KeyStroke.getKeyStroke(VK_BACK_SPACE, !isMacOS ? CTRL_DOWN_MASK : META_DOWN_MASK),
                DOCUMENT_STROKE = KeyStroke.getKeyStroke(VK_D, !isMacOS ? CTRL_DOWN_MASK : META_DOWN_MASK),
                EXIT_STROKE = KeyStroke.getKeyStroke(VK_W, !isMacOS ? CTRL_DOWN_MASK : META_DOWN_MASK);
    }
}
