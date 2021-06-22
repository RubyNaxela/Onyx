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

import com.rubynaxela.onyx.data.datatypes.Identifiable;

import java.util.Vector;

public class ObjectRow extends Vector<String> {

    private final String uuid;

    public ObjectRow(Identifiable object) {
        super();
        this.uuid = object.getUuid();
    }

    public String getObjectUuid() {
        return uuid;
    }
}
