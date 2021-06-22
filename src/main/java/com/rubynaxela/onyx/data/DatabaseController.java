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

import com.rubynaxela.onyx.Onyx;
import com.rubynaxela.onyx.data.datatypes.Identifiable;
import com.rubynaxela.onyx.data.datatypes.Invoice;
import com.rubynaxela.onyx.data.datatypes.raw.RawDatabase;
import com.rubynaxela.onyx.io.IOHandler;

import java.util.Arrays;
import java.util.Objects;

public final class DatabaseController {

    private final OnyxDatabase database;
    private final IOHandler ioHandler;

    public DatabaseController(Onyx instance) {
        this.database = instance.getDatabase();
        this.ioHandler = instance.getIOHandler();
    }

    public void loadDatabase() {
        final RawDatabase data = Objects.requireNonNull(ioHandler.parseDatabase());
        database.getObjects().addAll(Arrays.asList(data.getContractors()));
        database.getObjects().addAll(Arrays.asList(data.getOpenInvoices()));
        database.getObjects().addAll(Arrays.asList(data.getClosedInvoices()));
        database.getObjects().addAll(Arrays.asList(data.getClaims()));
        database.getObjects().addAll(Arrays.asList(data.getLiabilities()));
        database.getObjects().addAll(Arrays.asList(data.getContributions()));
        database.getObjects().addAll(Arrays.asList(data.getPayments()));
    }

    private void saveChanges() {
        ioHandler.exportDatabase(new RawDatabase(database));
    }

    public void addEntry(Identifiable entry) {
        database.getObjects().add(entry);
        saveChanges();
    }

    public void removeEntry(Identifiable entry) {
        database.getObjects().remove(Objects.requireNonNull(database.getObject(entry.getUuid())));
        saveChanges();
    }

    public void editEntry(Identifiable entry, Identifiable newValue) {
        database.getObjects().remove(Objects.requireNonNull(database.getObject(entry.getUuid())));
        database.getObjects().add(newValue);
        saveChanges();
    }
}