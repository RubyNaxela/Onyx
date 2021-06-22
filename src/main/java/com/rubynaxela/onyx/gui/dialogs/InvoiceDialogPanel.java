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

package com.rubynaxela.onyx.gui.dialogs;

import com.rubynaxela.onyx.data.DatabaseAccessor;
import com.rubynaxela.onyx.data.datatypes.Contractor;
import com.rubynaxela.onyx.data.datatypes.Invoice;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.event.DocumentListener;

public class InvoiceDialogPanel extends DefaultJPanel {

    public final JLabel idLabel, dateLabel, contractorLabel;
    public final JTextField idInput, dateInput;
    public final JComboBox<Contractor> contractorInput;
    public final JButton okButton;

    public InvoiceDialogPanel(Invoice editedElement, DatabaseAccessor databaseAccessor) {

        idLabel = new JLabel("Numer");
        dateLabel = new JLabel("Data");
        contractorLabel = new JLabel("Kontrahent");
        idInput = new JTextField();
        dateInput = new JTextField();
        contractorInput = new JComboBox<>(databaseAccessor.getContractorsVector());
        okButton = new JButton("OK");

        register(idLabel, Utils.gridElementSettings(0, 0));
        register(idInput, Utils.gridElementSettings(0, 1));
        register(dateLabel, Utils.gridElementSettings(1, 0));
        register(dateInput, Utils.gridElementSettings(1, 1));
        register(contractorLabel, Utils.gridElementSettings(2, 0, 2, 1));
        register(contractorInput, Utils.gridElementSettings(3, 0, 2, 1));

        idInput.setText(editedElement != null ? editedElement.getId() : "");
        dateInput.setText(editedElement != null ? editedElement.getDate() : "");

        final DocumentListener textFieldListener = createInputValidator();
        idInput.getDocument().addDocumentListener(textFieldListener);
        dateInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedElement != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
    }

    private AbstractValidInputListener createInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            protected boolean dataValid() {
                return !idInput.getText().equals("")
                       && !dateInput.getText().equals("");
            }
        };
    }
}
