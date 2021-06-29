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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("reference")
public abstract class Consideration extends Operation implements Referring {

    protected String paymentMethodUuid;
    protected String invoiceUuid;

    public String getInvoiceUuid() {
        return invoiceUuid;
    }

    public String getPaymentMethodUuid() {
        return paymentMethodUuid;
    }

    @Override
    public String getReference() {
        return invoiceUuid;
    }

    @Override
    public void removeReference() {
        invoiceUuid = null;
    }
}
