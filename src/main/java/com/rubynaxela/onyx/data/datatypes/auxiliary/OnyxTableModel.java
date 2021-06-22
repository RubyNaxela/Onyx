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

package com.rubynaxela.onyx.data.datatypes.auxiliary;

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.datatypes.OnyxObjects;

import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.Vector;

public class OnyxTableModel extends DefaultTableModel {

    public static Vector<String>
            contractorsTableHeaders = new Vector<>(Arrays.asList("Nazwa", "Szczegóły")),
            invoicesTableHeaders = new Vector<>(Arrays.asList("Numer", "Data", "Kontrahent", "Kwota brutto")),
            transactionsTableHeaders = new Vector<>(Arrays.asList("UUID", "Kontrahent", "Kwota"));

    private final DatabaseAccessor databaseAccessor;
    private Vector<ObjectRow> dataVector;
    private OnyxObjects currentTable;

    public OnyxTableModel(DatabaseAccessor databaseAccessor) {
        super();
        this.databaseAccessor = databaseAccessor;
    }

    public void display(OnyxObjects table) {
        currentTable = table;
        if (table == OnyxObjects.CONTRACTORS)
            setDataVector(dataVector = databaseAccessor.getContractorsTableVector(), contractorsTableHeaders);
        else if (table == OnyxObjects.OPEN_INVOICES)
            setDataVector(dataVector = databaseAccessor.getOpenInvoicesTableVector(), invoicesTableHeaders);
        else if (table == OnyxObjects.CLOSED_INVOICES)
            setDataVector(dataVector = databaseAccessor.getClosedInvoicesTableVector(), invoicesTableHeaders);
        else if (table == OnyxObjects.CLAIMS)
            setDataVector(dataVector = databaseAccessor.getClaimsTableVector(), transactionsTableHeaders);
        else if (table == OnyxObjects.LIABILITIES)
            setDataVector(dataVector = databaseAccessor.getLiabilitiesTableVector(), transactionsTableHeaders);
        else if (table == OnyxObjects.CONTRIBUTIONS)
            setDataVector(dataVector = databaseAccessor.getContributionsTableVector(), transactionsTableHeaders);
        else if (table == OnyxObjects.PAYMENTS)
            setDataVector(dataVector = databaseAccessor.getPaymentsTableVector(), transactionsTableHeaders);
    }

    public void refresh() {
        display(currentTable);
    }

    public OnyxObjects getCurrentTable() {
        return currentTable;
    }

    public ObjectRow getRow(int index) {
        return dataVector.get(index);
    }
}
