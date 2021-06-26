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

import java.util.Arrays;

/**
 * The {@code Language} class is a tool for setting the interface language
 *
 * @author Jacek Pawelski
 */
public class Language {

    public static final String
            ENGLISH_US = "en_US",
            POLISH = "pl_PL";

    private static String usedLanguage = ENGLISH_US;

    public static void useLanguage(String languageCode) {
        if (Arrays.asList(POLISH, ENGLISH_US).contains(languageCode)) usedLanguage = languageCode;
    }

    public static String getUsedLanguage() {
        return usedLanguage;
    }

}
