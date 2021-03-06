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

package com.rubynaxela.onyx.data.datatypes.databaseobjects;

public abstract class Consideration extends Operation {

    protected String paymentMethodUuid;

    public Consideration() {
    }

    public Consideration(Invoice invoice, String idPrefix) {
        super(invoice, idPrefix);
    }

    public String getPaymentMethodUuid() {
        return paymentMethodUuid;
    }
}
