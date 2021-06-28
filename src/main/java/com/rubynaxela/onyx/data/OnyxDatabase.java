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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public final class OnyxDatabase {

    private final TreeSet<? super Identifiable> objects = new TreeSet<>(Comparator.comparing(Identifiable::getUuid));
    private String companyName;

    @SuppressWarnings("unchecked")
    public void init(String companyName, List<? extends Identifiable>... lists) {
        this.companyName = companyName;
        for (List<? extends Identifiable> list : lists) objects.addAll(list);
    }

    public String getCompanyName() {
        return companyName;
    }

    public TreeSet<? super Identifiable> getAll() {
        return objects;
    }

    @SuppressWarnings("unchecked")
    public <T> LinkedList<T> getAllOfType(Class<T> type) {
        final LinkedList<T> list = new LinkedList<>();
        for (Object object : getAll()) if (type.isAssignableFrom(object.getClass())) list.add((T) object);
        return list;
    }

    public Identifiable get(String uuid) {
        return (Identifiable) getAll().stream()
                                      .filter(o -> ((Identifiable) o).getUuid().equals(uuid)).findFirst()
                                      .orElse(null);
    }

    public boolean contains(String uuid) {
        return objects.contains((Identifiable) () -> uuid);
    }
}
