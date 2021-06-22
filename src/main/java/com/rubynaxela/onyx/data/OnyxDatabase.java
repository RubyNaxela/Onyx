/*
 * Copyright (c) 2021 Jacek Pawelski a.k.a. RubyNaxela
 *
 * Licensed under the GNU General Public License v3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * The license is included in file 'LICENSE.txt', which is part of this
 * source code package. You may also obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/gpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rubynaxela.onyx.data;

import com.rubynaxela.onyx.data.datatypes.Identifiable;
import org.jetbrains.annotations.Contract;

import java.util.LinkedList;

public final class OnyxDatabase {

    private final LinkedList<? super Identifiable> objects = new LinkedList<>();

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
