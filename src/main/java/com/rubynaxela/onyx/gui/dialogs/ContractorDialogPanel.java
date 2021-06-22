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

import com.rubynaxela.onyx.data.datatypes.Contractor;
import com.rubynaxela.onyx.gui.components.DefaultJPanel;
import com.rubynaxela.onyx.util.Utils;

import javax.swing.*;
import javax.swing.event.DocumentListener;

public class ContractorDialogPanel extends DefaultJPanel {

    public final JLabel nameLabel, detailsLabel;
    public final JTextField nameInput;
    public final JTextArea detailsInput;
    public final JButton okButton;

    public ContractorDialogPanel(Contractor editedElement) {

        nameLabel = new JLabel("Nazwa");
        detailsLabel = new JLabel("Dane szczegółowe");
        nameInput = new JTextField();
        detailsInput = new JTextArea(5, 30);
        okButton = new JButton("OK");

        register(nameLabel, Utils.gridElementSettings(0, 0));
        register(nameInput, Utils.gridElementSettings(0, 1));
        register(detailsLabel, Utils.gridElementSettings(1, 0, 2, 1));
        detailsInput.setLineWrap(true);
        detailsInput.setWrapStyleWord(true);
        register(detailsInput, Utils.gridElementSettings(2, 0, 2, 1));

        nameInput.setText(editedElement != null ? editedElement.getName() : "");
        detailsInput.setText(editedElement != null ? editedElement.getDetails() : "");

        final DocumentListener textFieldListener = createInputValidator();
        nameInput.getDocument().addDocumentListener(textFieldListener);
        detailsInput.getDocument().addDocumentListener(textFieldListener);

        okButton.setEnabled(editedElement != null);
        okButton.addActionListener(e -> Utils.getOptionPane((JComponent) e.getSource()).setValue(okButton));
    }

    private AbstractValidInputListener createInputValidator() {
        return new AbstractValidInputListener(okButton) {
            @Override
            protected boolean dataValid() {
                return !nameInput.getText().equals("")
                       && !detailsInput.getText().equals("");
            }
        };
    }
}
