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

package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.data.datatypes.databaseobjects.Identifiable;
import org.jetbrains.annotations.Contract;

import java.util.LinkedList;
import java.util.List;

public final class OnyxDatabase {

    private final LinkedList<? super Identifiable> objects = new LinkedList<>();
    private String companyName;

    @SuppressWarnings("unchecked")
    public void init(String companyName, List<? extends Identifiable>... lists) {
        this.companyName = companyName;
        for (List<? extends Identifiable> list : lists) objects.addAll(list);
    }

    public String getCompanyName() {
        return companyName;
    }

    public LinkedList<? super Identifiable> getObjects() {
        return objects;
    }

    @SuppressWarnings("unchecked")
    @Contract(value = "!null -> new", pure = true)
    public <T> LinkedList<T> getObjects(Class<T> type) {
        LinkedList<T> list = new LinkedList<>();
        for (Object object : getObjects()) if (object.getClass().equals(type)) list.add((T) object);
        return list;
    }

    public Identifiable getObject(String uuid) {
        return (Identifiable) getObjects().stream().filter(
                c -> ((Identifiable) c).getUuid().equals(uuid)).findFirst().orElse(null);
    }
}
