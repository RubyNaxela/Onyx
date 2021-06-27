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

public abstract class Operation implements Identifiable {

    private String uuid, date, contractorUuid, description;
    private double amount;

    public String getDate() {
        return date;
    }

    public String getContractorUuid() {
        return contractorUuid;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String getUuid() {
        return uuid;
    }
}
