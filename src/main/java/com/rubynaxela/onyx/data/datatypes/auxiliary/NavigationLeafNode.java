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

import com.rubynaxela.onyx.data.datatypes.Identifiable;

import java.util.UUID;

public class NavigationLeafNode implements Identifiable {

    private final String uuid;
    public String value;

    public NavigationLeafNode(String value) {
        this.value = value;
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public String getUuid() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof NavigationLeafNode) {
            final NavigationLeafNode other = (NavigationLeafNode) o;
            return value.equals(other.value) && uuid.equals(other.uuid);
        } else return false;
    }

    @Override
    public String toString() {
        return value;
    }
}
