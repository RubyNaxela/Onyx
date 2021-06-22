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

package com.rubynaxela.onyx.data.datatypes.raw;

import com.rubynaxela.onyx.data.OnyxDatabase;
import com.rubynaxela.onyx.data.datatypes.*;

@SuppressWarnings("unused")
public class RawDatabase {

    private Contractor[] contractors;
    private OpenInvoice[] openInvoices;
    private ClosedInvoice[] closedInvoices;
    private Claim[] claims;
    private Liability[] liabilities;
    private Contribution[] contributions;
    private Payment[] payments;

    public RawDatabase() {
    }

    public RawDatabase(OnyxDatabase database) {
        this.contractors = database.getObjects(Contractor.class).toArray(new Contractor[0]);
        this.openInvoices = database.getObjects(OpenInvoice.class).toArray(new OpenInvoice[0]);
        this.closedInvoices = database.getObjects(ClosedInvoice.class).toArray(new ClosedInvoice[0]);
        this.claims = database.getObjects(Claim.class).toArray(new Claim[0]);
        this.liabilities = database.getObjects(Liability.class).toArray(new Liability[0]);
        this.contributions = database.getObjects(Contribution.class).toArray(new Contribution[0]);
        this.payments = database.getObjects(Payment.class).toArray(new Payment[0]);
    }

    public Contractor[] getContractors() {
        return contractors;
    }

    public void setContractors(Contractor[] contractors) {
        this.contractors = contractors;
    }

    public OpenInvoice[] getOpenInvoices() {
        return openInvoices;
    }

    public void setOpenInvoices(OpenInvoice[] openInvoices) {
        this.openInvoices = openInvoices;
    }

    public ClosedInvoice[] getClosedInvoices() {
        return closedInvoices;
    }

    public void setClosedInvoices(ClosedInvoice[] closedInvoices) {
        this.closedInvoices = closedInvoices;
    }

    public Claim[] getClaims() {
        return claims;
    }

    public void setClaims(Claim[] claims) {
        this.claims = claims;
    }

    public Liability[] getLiabilities() {
        return liabilities;
    }

    public void setLiabilities(Liability[] liabilities) {
        this.liabilities = liabilities;
    }

    public Contribution[] getContributions() {
        return contributions;
    }

    public void setContributions(Contribution[] contributions) {
        this.contributions = contributions;
    }

    public Payment[] getPayments() {
        return payments;
    }

    public void setPayments(Payment[] payments) {
        this.payments = payments;
    }
}
