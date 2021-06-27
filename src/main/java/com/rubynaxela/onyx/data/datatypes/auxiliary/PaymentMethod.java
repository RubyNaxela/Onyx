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
import com.rubynaxela.onyx.util.Reference;

import java.util.Arrays;
import java.util.UUID;

public enum PaymentMethod implements Identifiable {

    UNKNOWN(new UUID(4, 0).toString(), Reference.getString("value.payment_method.unknown")),
    TRANSFER(new UUID(4, 1).toString(), Reference.getString("value.payment_method.transfer")),
    CASH(new UUID(4, 2).toString(), Reference.getString("value.payment_method.cash"));

    private final String uuid, label;

    PaymentMethod(String uuid, String label) {
        this.uuid = uuid;
        this.label = label;
    }

    public static PaymentMethod get(String uuid) {
        if (uuid == null) return UNKNOWN;
        return Arrays.stream(PaymentMethod.values()).filter(m -> m.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public String getUuid() {
        return uuid;
    }
}
