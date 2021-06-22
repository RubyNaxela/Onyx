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

public class Monetary {

    private long wholePart, hundredthPart;

    public Monetary(int wholePart, int hundredthPart) {
        if (wholePart < 0 || hundredthPart < 0) throw new IllegalArgumentException("");
        this.wholePart = wholePart;
        this.hundredthPart = hundredthPart;
    }

    public void add(Monetary other) {
        this.wholePart += other.wholePart + (this.hundredthPart + other.hundredthPart) / 100;
        this.hundredthPart = (this.hundredthPart + other.hundredthPart) % 100;
    }

    @Override
    public String toString() {
        return wholePart + (hundredthPart < 10 ? ".0" : ".") + hundredthPart;
    }
}
