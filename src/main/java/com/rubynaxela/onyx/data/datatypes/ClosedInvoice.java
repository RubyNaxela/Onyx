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

package com.rubynaxela.onyx.data.datatypes;

import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;
import com.rubynaxela.onyx.util.Reference;

import java.util.Arrays;
import java.util.UUID;

@SuppressWarnings("unused")
public class ClosedInvoice extends Invoice {

    private String paymentMethodUuid;

    public ClosedInvoice() {
    }

    public ClosedInvoice(ImportedInvoice data) {
        this.uuid = UUID.randomUUID().toString();
        this.id = data.getId();
        this.date = data.getDate();
        this.contractorUuid = "?";
        this.paymentMethodUuid = null;
        this.items = data.getItems();
    }

    public ClosedInvoice(String uuid, String id, String date, String contractorUuid,
                         String paymentMethodUuid, InvoiceItem[] items) {
        this.uuid = uuid;
        this.id = id;
        this.date = date;
        this.contractorUuid = contractorUuid;
        this.paymentMethodUuid = paymentMethodUuid;
        this.items = items;
    }

    public String getPaymentMethodUuid() {
        return paymentMethodUuid;
    }

    public void setPaymentMethodUuid(String paymentMethodUuid) {
        this.paymentMethodUuid = paymentMethodUuid;
    }

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
}
