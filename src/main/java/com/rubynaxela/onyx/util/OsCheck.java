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

import java.util.Locale;

/**
 * The {@code OsCheck} class is a tool for checking the operating system on which
 * the program is running. Uses the {@code os.name} Java property to determine the system
 *
 * @author Jacek Pawelski
 */
public final class OsCheck {

    public static OSType getOperatingSystemType() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            return OSType.MAC_OS;
        } else if (os.contains("win")) {
            return OSType.WINDOWS;
        } else if (os.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    public enum OSType {
        WINDOWS, MAC_OS, LINUX, OTHER
    }
}