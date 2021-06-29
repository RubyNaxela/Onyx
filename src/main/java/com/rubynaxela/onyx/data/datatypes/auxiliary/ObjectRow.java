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

package com.rubynaxela.onyx.data.datatypes.auxiliary;

import com.rubynaxela.onyx.util.Uuid;

import java.util.Arrays;
import java.util.Vector;

public class ObjectRow extends Vector<String> {

    private final String uuid;

    public ObjectRow(@Uuid String uuid, String... data) {
        super();
        this.uuid = uuid;
        this.addAll(Arrays.asList(data));
    }

    public String getObjectUuid() {
        return uuid;
    }
}
