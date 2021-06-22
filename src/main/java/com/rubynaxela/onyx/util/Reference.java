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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

public class Reference {

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
