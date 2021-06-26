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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.Objects;

public class Reference {

    private static Map<String, String> primaryDictionary, backupDictionary;

    public static void init(IOHandler ioHandler, MessageDialogsHandler messageDialogsHandler) {
        primaryDictionary = ioHandler.parseLanguageFile(Language.getUsedLanguage());
        backupDictionary = ioHandler.parseLanguageFile(Language.ENGLISH_US);
        if (primaryDictionary == null && backupDictionary == null)
            messageDialogsHandler.showError("Attempt to read the language file has resulted an unhandled error.", true);
    }

    /**
     * Returns a localized string from a given key. If not found in primary dictionary (that is, by default, the
     * currently used language file), then searches in the backup dictionary (that is, by default, English (US)). If
     * nothing is found in both, returns the key itself
     *
     * @param key dictionary key of the string
     * @return localized string
     */
    @NotNull
    public static String getString(@NotNull String key) {
        if (primaryDictionary.containsKey(key))
            return Objects.requireNonNull(primaryDictionary.get(key));
        else if (backupDictionary.containsKey(key))
            return Objects.requireNonNull(backupDictionary.get(key));
        else
            return key;
    }

    /**
     * Returns a localized and formatted string using the specified format string from a given key and arguments. If the key
     * is not found in primary dictionary (that is, by default, the currently used language file), then searches in the backup
     * dictionary (that is, by default, English (US)). If the key is not found in both dictionaries, returns the key itself
     *
     * @param key  dictionary key of the string
     * @param args arguments referenced by the format specifiers in the format string
     * @return localized string
     * @see java.lang.String#format(String, Object...)
     */
    @NotNull
    public static String getFormatString(@NotNull String key, Object... args) {
        if (primaryDictionary.containsKey(key))
            return String.format(Objects.requireNonNull(primaryDictionary.get(key)), args);
        else if (backupDictionary.containsKey(key))
            return String.format(Objects.requireNonNull(backupDictionary.get(key)), args);
        else
            return key;
    }

    /**
     * Returns a localized string from a given key. If not found in primary dictionary (that is, by default, the
     * currently used language file), then searches in the backup dictionary (that is, by default, English (US)). If
     * nothing is found in both, returns the fallback value
     *
     * @param key           dictionary key of the string
     * @param fallbackValue the fallback value
     * @return localized string or the fallback value
     */
    @NotNull
    public static String getString(@NotNull String key, @NotNull String fallbackValue) {
        return (primaryDictionary.containsKey(key) || backupDictionary.containsKey(key)) ? getString(key) : fallbackValue;
    }

    @Nullable
    public static Icon getIcon(@NotNull String key) {
        switch (key) {
            case "dialog.info":
                return new FlatOptionPaneInformationIcon();
            case "dialog.warning":
                return new FlatOptionPaneWarningIcon();
            case "dialog.error":
                return new FlatOptionPaneErrorIcon();
            case "dialog.question":
                return new FlatOptionPaneQuestionIcon();
            case "dialog.data":
                return new FlatOptionPaneAbstractIcon("Actions.Green", "Actions.Green") {
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
            default:
                return null;
        }
    }
}
