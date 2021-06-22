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

import java.util.UUID;

public class NavigationLeafNode implements Identifiable {

    private final String uuid;
    public String value;

    public NavigationLeafNode(String value) {
        this.value = value;
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NavigationLeafNode) {
            final NavigationLeafNode other = (NavigationLeafNode) o;
            return value.equals(other.value) && uuid.equals(other.uuid);
        } else return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
