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

package com.rubynaxela.onyx.io;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rubynaxela.onyx.data.datatypes.raw.ImportedInvoice;
import com.rubynaxela.onyx.data.datatypes.raw.RawDatabase;

import java.io.File;
import java.io.IOException;

/**
 * The {@code FileIOHandler} class is responsible for handling JSON files - reading
 * from a file, deserialization and writing to directly from the database stored
 * by the program and serialization of data from the database and writing to a file
 *
 * @author Jacek Pawelski
 */
public final class IOHandler {

    private static final File DATA_FILE = new File("database.json");
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    public RawDatabase parseDatabase() {
        try {
            return JSON_MAPPER.readValue(DATA_FILE, RawDatabase.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportDatabase(RawDatabase data) {
        try {
            JSON_MAPPER.writeValue(DATA_FILE, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ImportedInvoice parseInvoice(File invoiceFile) throws IOException {
        return JSON_MAPPER.readValue(invoiceFile, ImportedInvoice.class);
    }
}
