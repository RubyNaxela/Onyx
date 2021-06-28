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

import com.rubynaxela.onyx.data.datatypes.databaseobjects.Identifiable;

import java.util.UUID;

public class LeafLabel implements Identifiable {

    private final String uuid;
    private final String value;

    private final ObjectType associatedGroup;

    public LeafLabel(String value, ObjectType associatedGroup) {
        this.value = value;
        this.associatedGroup = associatedGroup;
        uuid = UUID.randomUUID().toString();
    }

    public ObjectType getAssociatedGroup() {
        return associatedGroup;
    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LeafLabel) {
            final LeafLabel other = (LeafLabel) o;
            return value.equals(other.value) && uuid.equals(other.uuid);
        } else return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
