/*
 *     This file is part of wraelclast-online.
 *
 *     wraelclast-online is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     wraelclast-online is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with wraelclast-online.  If not, see <http://www.gnu.org/licenses/>.
 */

package wo.trade;

/**
 * Created 11/1/2016.
 */
public enum Rarity {
    normal,
    magic,
    rare,
    unique,
    gem,
    currency,
    divinationcard,
    unknown;

    public static Rarity valueOf(int ordinal) {
        for (Rarity r : values()) {
            if (r.ordinal() == ordinal) return r;
        }
        return unknown;
    }
}
